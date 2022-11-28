package bank.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class Bank {
    private String bankName;
    private String bankCode;
    private List<Account> bankAccounts;
    private List<Customer> customers;
    private BigDecimal interestRate;


    public Bank(String bankName, BigDecimal interestRate) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankAccounts = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.interestRate = interestRate;
    }

    public Customer getCustomerOrNull(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerID)) {
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
            if (customer.getCustomerId().equals(customerID)) {
                return false;
            }
        }

        return true;
    }

    public void registerCustomer(String customerID, String password, String customerName) {
        Customer newCustomer = new Customer(customerID, password, customerName);
        customers.add(newCustomer);
    }

    public void deleteAccount(Account account) {
        bankAccounts.remove(account);
    }

    public void checkAccountByCustomerName(Customer customer) { //소유자 명으로 계좌 조회
        for (Account account : bankAccounts) {
            if (customer.getName().equals(account.getOwnerName())) {
                String bankName = account.getBankName();
                String accountNum = account.getAccountNumber();

                BigDecimal balance = account.getBalance();
                System.out.printf("은행 : %s, 계좌번호 : %s, 잔고 : %s원", bankName, accountNum, balance);

            }
        }
    }

    public void findEveryAccountFromCustomerId(Customer customer) { //모든 계좌 목록 조회
        List<Account> temp = customer.getCustomerAccounts();
        for (Account account : temp) {
            System.out.println(account);
        }
    }

    public Account registerAccount(String ownerName) {
        String bankNumber = createBankNumber();

        for (Account Account : bankAccounts) {
            if (Account.getAccountNumber().equals(bankNumber)) {
                bankNumber = createBankNumber();
            }
        }

        Account newAccount = new Account(ownerName, bankNumber, this.bankName, this.interestRate);
        bankAccounts.add(newAccount);
        return newAccount;
    }

    public Account findAccountOrNull(String accountNumber){
        for (Account bankAccount : bankAccounts) {
            if(bankAccount.getAccountNumber().equals(accountNumber)){
                return bankAccount;
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addAccount(Account account) {
        bankAccounts.add(account);
    }

    private String createBankNumber() {
        StringBuilder bankNumBuilder = new StringBuilder();
        bankNumBuilder.append(this.bankCode);
        bankNumBuilder.append(String.format("%03d", (int)(Math.random() * 100)));
        bankNumBuilder.append(String.format("%06d", (int)(Math.random() * 100000)));
        return bankNumBuilder.toString();
    }


}
