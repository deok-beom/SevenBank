package bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Bank {
    private String bankName;
    private List<Account> bankAccounts;
    private List<Customer> customers;

    public Bank() {
    }

    public Bank(String bankName) {
        this.bankName = bankName;
        this.bankAccounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public Customer getCustomerOrNull(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }

        return null;
    }

    public boolean loginCustomer(Customer customer, String password) {
        if (customer.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    public boolean checkDuplicateID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return false;
            }
        }

        return true;
    }

    public void registerCustomer(String customerID, String password, String customerName) {
        Customer newCustomer = new Customer(customerID, password, customerName);
        customers.add(newCustomer);
    }


    public void deleteAccount(Customer customer) {
        for (Account account : bankAccounts) {
            if (customer.getName().equals(account.getOwnerName())) {

                bankAccounts.remove(customer);
            }
        }
    }

    public void checkAccountByCustomerName(Customer customer) { //소유자 명으로 계좌 조회
        for (Account account : bankAccounts) {
            if (customer.getName().equals(account.getOwnerName())) {
                String bankName = account.getBankName();
                String accountNum = account.getAccountNumber();
                int balance = account.getBalance();
                System.out.printf("은행 : %s, 계좌번호 : %s, 잔고 : %d원", bankName, accountNum, balance);
            }
        }
    }

    public void findEveryAccountFromCustomerID(Customer customer) { //모든 계좌 목록 조회
        List<Account> temp = customer.getAccounts();
        for (Account account : temp) {
            System.out.println(account);
        }
    }

    public void registerAccount(String ownerName) {
        String bankNumber = createBankNumber();

        for (Account Account : bankAccounts) {
            if (Account.getAccountNumber().equals(bankNumber)) {
                bankNumber = createBankNumber();
            }
        }

        Account newAccount = new Account(ownerName, bankNumber, this.bankName);
        bankAccounts.add(newAccount);
    }

    private String createBankNumber() {
        StringBuilder bankNumBuilder = new StringBuilder();
        bankNumBuilder.append("110");
        bankNumBuilder.append((Math.random() * 100));
        bankNumBuilder.append((Math.random() * 100000));
        return bankNumBuilder.toString();
    }
}
