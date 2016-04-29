package com.ofg.loans.service.risk;

import com.ofg.loans.model.LoanApplication;

/**
 * Please implement this interface. 
 * Do not change signature of the method Boolean approve(LoanApplication loanApplication).
 */
public interface RiskService {

    /**
     * Maximum allowed loan amount
     */
    Integer MAXIMUM_LOAN_AMOUNT = 100;

    /**
     * Maximum attempts count for a client per single IP address.
     */
    Integer MAXIMUM_ATTEMPTS_PER_IP_ADDRESS = 3;

    /**
     * Analyze provided loan application for possible risk.
     * <p/>
     * Risk is considered too high in the next cases:
     * <p/>
     * 1. Loan application is created outside of the time frame 06:00..00:00 with maximum allowed amount.
     * 2. Client reached the maximum application attempts count per day from a single IP address.
     * <p/>
     * Number and combination of risk rules are subject to change in the future.
     *
     * @param loanApplication
     * @return true in case no risk for provided loan application found.
     */
    Boolean approve(LoanApplication loanApplication);


}
