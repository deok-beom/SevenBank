package bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    private String name;
    private List<Account> customerAccounts;
    private String customerId; // 동명 이인일 수 있어서 이걸로 식별
    private String customerPassword;

    public Customer(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public Customer(String name, String customerId, String customerPassword) {
        this.name = name;
        this.customerAccounts = new ArrayList<>();
        this.customerId = customerId;
        this.customerPassword = customerPassword;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return customerAccounts;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

}
