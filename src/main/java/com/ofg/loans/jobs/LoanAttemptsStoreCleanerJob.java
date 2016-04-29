package com.ofg.loans.jobs;

import com.ofg.loans.store.LoanAttemptsStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ivsi on 4/29/16.
 * <p>
 * This class is responsible for cleaning the collection every day at midnight (00:00)
 * Using this job we ensure that each of the users can make up to MAXIMUM_ATTEMPTS_PER_IP_ADDRESS attempts in the day.
 */
@Component
public class LoanAttemptsStoreCleanerJob {

    @Autowired
    LoanAttemptsStore loanAttemptsStore;

    /**
     * Clear previously stored IP Addresses and users every day at 00:00
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearLoanAttemptsStore() {
        loanAttemptsStore.getClientsAttemptsIps().clear();
    }



}
