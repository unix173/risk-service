package com.ofg.loans.model;

import java.time.LocalTime;

/**
 * Loan application entity.
 */
public class LoanApplication {

    private Client client;

    private LocalTime timeOfSubmitting;

    private int loanAmount;

    public LoanApplication() {
        //empty constructor
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LocalTime getTimeOfSubmitting() {
        return timeOfSubmitting;
    }

    public void setTimeOfSubmitting(LocalTime timeOfSubmitting) {
        this.timeOfSubmitting = timeOfSubmitting;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


}
