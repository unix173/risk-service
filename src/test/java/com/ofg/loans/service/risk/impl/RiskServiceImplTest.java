package com.ofg.loans.service.risk.impl;

import com.ofg.loans.model.Client;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.service.risk.RiskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalTime;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ivsi on 4/29/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/applicationContext.xml")
public class RiskServiceImplTest {

    @Autowired
    RiskService riskService;

    /**
     *  Non risky
     */

    @Test
    public void approve_WhenGoodAmountGoodIpAttemptsGoodTime_ThenReturnTrue() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Simic");
        client.setIpAddress("192.168.0.101");

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClient(client);
        loanApplication.setLoanAmount(50);
        loanApplication.setTimeOfSubmitting(LocalTime.NOON);

        assertTrue(riskService.approve(loanApplication));
    }

    @Test
    public void approve_WhenGoodAmountSeveralDifferentIpAttemptsGoodTime_ThenReturnTrue() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Simic");
        client.setIpAddress("192.168.0.101");

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClient(client);
        loanApplication.setLoanAmount(50);
        loanApplication.setTimeOfSubmitting(LocalTime.NOON);

        riskService.approve(loanApplication);

        client.setIpAddress("192.168.0.101");
        riskService.approve(loanApplication);

        client.setIpAddress("192.168.0.101");
        riskService.approve(loanApplication);

        client.setIpAddress("192.168.0.102");
        riskService.approve(loanApplication);

        client.setIpAddress("192.168.0.102");
        riskService.approve(loanApplication);

        riskService.approve(loanApplication);
        client.setIpAddress("192.168.0.103");

        riskService.approve(loanApplication);
        client.setIpAddress("192.168.0.103");

        assertTrue(riskService.approve(loanApplication));
    }

    /**
     * Risky
     */

    //this one could be also impossible, because it goes over the limited amount

    @Test
    public void approve_WhenBadAmountGoodIpAttemptsGoodTime_ThenReturnFalse() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Simic");
        client.setIpAddress("192.168.0.101");

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClient(client);
        loanApplication.setLoanAmount(150);
        loanApplication.setTimeOfSubmitting(LocalTime.NOON);

        assertFalse(riskService.approve(loanApplication));
    }

    @Test
    public void approve_WhenMaxAmountGoodIpAttemptsBadTime_ThenReturnFalse() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Simic");
        client.setIpAddress("192.168.0.101");

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClient(client);
        loanApplication.setLoanAmount(100);
        loanApplication.setTimeOfSubmitting(LocalTime.MIDNIGHT.plusHours(2));

        assertFalse(riskService.approve(loanApplication));
    }

    @Test
    public void approve_WhenGoodAmountBadIpAttemptsGoodTime_ThenReturnFalse() {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setLastName("Simic");
        client.setIpAddress("192.168.0.101");

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setClient(client);
        loanApplication.setLoanAmount(50);
        loanApplication.setTimeOfSubmitting(LocalTime.NOON);

        riskService.approve(loanApplication);
        riskService.approve(loanApplication);
        riskService.approve(loanApplication);

        assertFalse(riskService.approve(loanApplication));
    }

}