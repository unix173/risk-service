package com.ofg.loans.service.risk.impl;

import com.ofg.loans.store.LoanAttemptsStore;
import com.ofg.loans.model.Client;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.service.risk.RiskService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ivsi on 4/29/16.
 */
@Component("riskService")
public class RiskServiceImpl implements RiskService {

    @Autowired
    LoanAttemptsStore loanAttemptsStore;

    /**
     * @param loanApplication
     * @return true in case that the loan is not requested between 00:00 and 06:00 with maximum number
     * or
     * true if case that the loan request is not attempted more than 3 times per user from same IPAddress
     */
    @Override
    public Boolean approve(LoanApplication loanApplication) {
        if (this.isAttemptedAtRiskyTime(loanApplication)) {
            System.out.printf("Time: NOT OK");
            if (loanApplication.getLoanAmount() >= MAXIMUM_LOAN_AMOUNT) {
                System.out.println("Amount: NOT OK");
                return false;
            } else {
                System.out.println("Amount: OK");
                if (isAttemptLimitExceded(loanApplication)) {
                    System.out.println("Singe IPAddress attempts: NOT OK");
                    return false;
                } else {
                    System.out.println("Singe IPAddress attempts: OK");
                    return true;
                }
            }
        } else {
            System.out.println("TIME: OK");
            if (loanApplication.getLoanAmount() >= MAXIMUM_LOAN_AMOUNT) {
                System.out.println("Amount: NOT OK");
                return false;
            } else {
                System.out.println("Amount: OK");
                if (isAttemptLimitExceded(loanApplication)) {
                    System.out.println("Singe IPAddress attempts: NOT OK");
                    return false;
                } else {
                    System.out.println("Singe IPAddress attempts: OK");
                    return true;
                }
            }
        }
    }

    /**
     * Check if the user exceeded the limit of attempts with single IpAddress
     * <p>
     * Here we implement the logic how we store already attempted requests based on unique username and IP
     *
     * @param loanApplication
     * @return true in case that the number of attempts of one user from single IP exceeds.
     */
    private boolean isAttemptLimitExceded(LoanApplication loanApplication) {
        Client client = loanApplication.getClient();
        String currentIpAddress = loanApplication.getClient().getIpAddress();
        //check if client exists
        if (loanAttemptsStore.getClientsAttemptsIps().containsKey(client.getUserKey())) {
            List<String> existingIpAddresses = loanAttemptsStore.getClientsAttemptsIps().get(client.getUserKey());
            //check if the IPAddress appears in the list more than the defined limit number
            if (Collections.frequency(existingIpAddresses, currentIpAddress) >= MAXIMUM_ATTEMPTS_PER_IP_ADDRESS) {
                //the limit is exceeded
                System.out.println("Requests number for single IPAddress per client reached the defined limit");
                return true;
            } else {
                //store the IPAddress in the list if the limit is not exceeded
                existingIpAddresses.add(currentIpAddress);
                return false;
            }
        } else {
            //client is not found in the map
            //add the current client and initialize new list of future IPs with added IP for first attempt
            List<String> newUserIpAddresses = new ArrayList<>();
            newUserIpAddresses.add(client.getIpAddress());
            loanAttemptsStore.getClientsAttemptsIps().put(client.getUserKey(), newUserIpAddresses);
            return false;
        }
    }

    /**
     * Check if the user attempted to take loan at risky time
     *
     * @param loanApplication
     * @return true in case that the request was made between 00:00 and 06:00 AM
     */
    private boolean isAttemptedAtRiskyTime(LoanApplication loanApplication) {
        return (loanApplication.getTimeOfSubmitting().compareTo(LocalTime.MIDNIGHT) >= 0 &&
                loanApplication.getTimeOfSubmitting().compareTo(LocalTime.MIDNIGHT.plusHours(6)) <= 0);
    }


}
