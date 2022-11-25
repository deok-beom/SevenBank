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
        for (Account account : bankAccounts) {
            if (customer.getName().equals(account.getOwnerName())) {
                bankAccounts.remove(customer);
            }
        }
    }

    public void checkAccountByCustomerName(Customer customer){ //소유자 명으로 계좌 조회
        for (Account account : bankAccounts) {
            if (customer.getName().equals(account.getOwnerName())) {
                String bankName = account.getBankName();
                String accountNum = account.getAccountNumber();
                int balance = account.getBalance();
                System.out.printf("은행 : %s, 계좌번호 : %s, 잔고 : %d원", bankName, accountNum, balance);
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