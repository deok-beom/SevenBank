package bank.application;

import bank.db.BankDB;
import bank.entity.Account;
import bank.entity.Bank;
import bank.entity.Customer;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountApp {
    private static HashMap<String, Account> accounts = new HashMap<>(); //accounts 계좌들 해쉬맵 구조로 저장

    public static Account registerAccount(String ownerId, int bankIndex) { //ownerId와 bankName을 받음
        Customer customer = CustomerApp.getCustomerOrNull(ownerId); //customers 해쉬맵에서 ownerId 키에 해당하는 customer객체를 받아옴
        Bank bank = BankDB.getBankByIndex(bankIndex); //bank의 인덱스를 bankDB에서 받아옴

        String bankNumber = createBankNumber(bank.getBankCode());  //bank에 저장된 코드를 가져와서 계좌번호 생성

        if (accounts.containsKey(bankNumber)) {
            bankNumber = createBankNumber(bank.getBankCode()); //중복되면 새로운 계좌번호 생성
        }

        Account newAccount = new Account(customer.getName(), ownerId, bankNumber, bank.getBankName(),
                bank.getInterestRate());
        accounts.put(bankNumber, newAccount);
        customer.addCustomerAccount(newAccount); //customer의 accountlist에도 새로운 계좌 넣어줌
        return newAccount;
    }

    public static boolean unregisterAccount(String ownerId, int accountIndex) { //ownerId와 accountIndex를 받아서 계좌를 삭제
        Customer customer = CustomerApp.getCustomerOrNull(ownerId); //ownerId로 customer 받아옴
        Account targetAccount = customer.getAccount(accountIndex); //accountIndex로 customer의 accountIndex로 account 받아옴
        if (targetAccount.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            return false; //잔고가 0원이 아니면 삭제불가
        } else {
            customer.deleteCustomerAccount(targetAccount);
            accounts.remove(targetAccount.getAccountNumber());

            return true;
        }
    }

    public static Account getAccountByBankNumberOrNull(String bankNumber) {
        return accounts.get(bankNumber);
    } //뱅크넘버에 해당하는 계좌를 리턴

    private static String createBankNumber(String bankCode) {
        StringBuilder bankNumBuilder = new StringBuilder();
        bankNumBuilder.append(bankCode);
        bankNumBuilder.append(String.format("%03d", (int)(Math.random() * 100))); //계좌번호3자리 랜덤으로 받아옴
        bankNumBuilder.append(String.format("%06d", (int)(Math.random() * 100000))); //계좌번호6자리 랜덤으로 받아옴
        return bankNumBuilder.toString();
    }

}
