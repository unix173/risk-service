package com.ofg.loans.store;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ivsi on 4/29/16.
 *
 * LoanAttemptsStore holds the collection that has mapped users to IPAddresses they used
 * The collection is shared, so it must be thread safe
 * Database would be the correct way to handle this, but this was just for the demo
 *
 */

@Component
public class LoanAttemptsStore {

    private Map<String, List<String>> clientsAttemptsIps = new ConcurrentHashMap<>();

    public Map<String, List<String>> getClientsAttemptsIps() {
        return clientsAttemptsIps;
    }
}
