package bank.application;

import bank.db.BankDB;
import bank.entity.Bank;

import java.math.BigDecimal;
import java.util.HashMap;

public class BankApp {
    private static HashMap<String, Bank> banks = new HashMap<>();

    public static void registerBank(String bankName, BigDecimal interestRate, String bankCode) {
        if (banks.containsKey(bankCode)) {
            return; //뱅크 이름과, 이율, 코드 받아서 해쉬맵에 저장, 이때 키는 뱅크코드임
        }
        Bank bank = new Bank(bankName, interestRate, bankCode); //매개변수 받아서 bank객체 생성
        banks.put(bankCode, bank); //해쉬맵에 넣어줌
        BankDB.addBank(bank); //뱅크디비의 뱅크리스트에도 넣어줌
    }
}
