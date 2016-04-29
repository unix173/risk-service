package com.ofg.loans.model;

/**
 * Client entity.
 */
public class Client {

    private String firstName;

    private String lastName;

    // For simplicity, I will assume here that with the user, we get the IPAddress from the request
    private String ipAddress;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     *
     * @return "unique" key for the user to store in the map of attempts
     */
    public String getUserKey() {
        return firstName + lastName;
    }
}
