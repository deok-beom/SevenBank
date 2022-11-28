package bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String customerID;
    private String password;
    private String name;
    private List<Account> customerAccounts;

    public Customer(String customerID, String password, String name) {
        this.customerID = customerID;
        this.password = password;
        this.name = name;
        this.customerAccounts = new ArrayList<>();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return customerAccounts;
    }

    public Customer(String name, String customerID) {
        this.name = name;
        this.customerID = customerID;
    }
}
