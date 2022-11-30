package bank;


import bank.application.AccountApp;
import bank.application.BankApp;
import bank.application.CustomerApp;
import bank.entity.Account;
import bank.view.LoginAndRegisterView;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {

        BankApp.registerBank("한국은행", BigDecimal.valueOf(0.01), "101");
        BankApp.registerBank("KB국민은행", BigDecimal.valueOf(0.02), "018");
        BankApp.registerBank("하나은행", BigDecimal.valueOf(0.01), "301");
        BankApp.registerBank("NH농협은행", BigDecimal.valueOf(0.02), "016");
        BankApp.registerBank("우리은행", BigDecimal.valueOf(0.01), "020");
        BankApp.registerBank("신한은행", BigDecimal.valueOf(0.01), "110");
        BankApp.registerBank("카카오뱅크", BigDecimal.valueOf(0.02), "099");

        CustomerApp.registerCustomer("pororo", "1234", "뽀로로");
        Account account = AccountApp.registerAccount("pororo", 6);
        account.deposit(BigDecimal.valueOf(10_000));

        LoginAndRegisterView.printLogo();

        while (true) {
            LoginAndRegisterView.showBeginningUI();
        }
    }
}
