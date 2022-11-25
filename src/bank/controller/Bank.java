package bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Bank {
    private String bankName;
    private List<Account> bankAccounts;
    private List<Customer> customers;

    public Bank(String bankName) {
        this.bankAccounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void registerCustomer(Customer customer) {
        customers.add(customer);
    }

    public void reviseAccount(Customer customer) {
    }

    public void deleteAccount(Customer customer) {
        for(Account account : bankAccounts) {
            if(customer.getName().equals(account.getOwnerName())) {
                bankAccounts.remove(customer);
            }
        }
    }

    public void findEveryAccountFromCustomerId(Customer customer) { //모든 계좌 정보 조회 기능
        String customerId = customer.getCustomerId();
        List<Account> accounts = customer.getAccounts();
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}