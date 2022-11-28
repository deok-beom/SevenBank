package bank.application;

import bank.controller.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import bank.controller.Bank;
import bank.controller.ETradeType;
import bank.controller.History;
import bank.view.BankingView;
import bank.view.LoginAndRegisterView;
import bank.view.PrintLogo;

public class Main {

    public static void main(String[] args) {
        PrintLogo.printLogo();
        Bank bank = new Bank();
        LoginAndRegisterView loginAndRegisterView = new LoginAndRegisterView();
        loginAndRegisterView.showUIBeginning(bank);
    }
}
