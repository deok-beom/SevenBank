package bank.view;

import java.util.List;
import java.util.Scanner;

import bank.application.AccountApp;
import bank.db.BankDB;
import bank.entity.Account;
import bank.entity.Bank;
import bank.entity.Customer;

public class AccountView {
    public static void showMainAccountUI(Customer customer) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("<원하시는 기능을 선택해주세요>");
        System.out.println("1. 계좌 선택");
        System.out.println("2. 계좌 생성");
        System.out.println("3. 계좌 삭제");
        System.out.println("4. 로그아웃");
        System.out.println("5. 종료");
        System.out.println("========================================");
        System.out.print("번호 입력 : ");
        String s = scanner.nextLine();

        if (s.equals("1")) {
            ViewMethod.jump();
            BankingView.showAccountListUI(customer);
        } else if (s.equals("2")) {
            ViewMethod.jump();
            showCreateAccountUI(customer);
        } else if (s.equals("3")) {
            ViewMethod.jump();
            showDeleteAccountUI(customer);
        } else if (s.equals("4")) {
            ViewMethod.jump();
            System.out.println("로그아웃 되었습니다🙋");
            LoginAndRegisterView.showBeginningUI();
        } else if (s.equals("5")) {
            ViewMethod.jump();
            ViewMethod.printExitMessage();
            System.exit(0);
        } else {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showMainAccountUI(customer);
        }
    }

    public static void showCreateAccountUI(Customer customer) {
        Scanner selectScanner = new Scanner(System.in);
        List<Bank> banks = BankDB.getBanks();
        System.out.println("========================================");
        System.out.println("<원하시는 은행을 선택해주세요>");
        System.out.println("0. 돌아가기");
        for (int i = 0; i < banks.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, banks.get(i).getBankName()));
        }
        System.out.print("번호 입력 : ");
        String selectInput = selectScanner.next();
        for (int i = 0; i < selectInput.length(); i++) {
            char moveChar = selectInput.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showCreateAccountUI(customer);
                return;
            }
        }
        int bankIndex = Integer.parseInt(selectInput);

        if (bankIndex < 0 || bankIndex > banks.size()) {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showCreateAccountUI(customer);
            return;
        } else if (bankIndex == 0) {
            showMainAccountUI(customer);
        }

        System.out.print("계좌 생성중.");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("showCreateAccountUI[line, 50] : InterruptException!");
            }
            System.out.print(".");
        }
        ViewMethod.jump();
        Account newAccount = AccountApp.registerAccount(customer.getCustomerId(), bankIndex - 1);
        System.out.println("생성 완료 되었습니다👍");
        System.out.printf("🤑[%s] %s%s", newAccount.getBankName(), newAccount.getAccountNumberWithHypen(),
                System.lineSeparator());
        showMainAccountUI(customer);
    }

    public static void showDeleteAccountUI(Customer customer) {
        Scanner selectScanner = new Scanner(System.in);
        List<Account> accounts = customer.getCustomerAccounts();
        if (accounts.size() == 0) {
            ViewMethod.jump();
            System.out.println("👀삭제할 계좌가 없습니다");
            showMainAccountUI(customer);
        }

        System.out.println("========================================");
        System.out.println("<삭제할 계좌를 선택해주세요>");
        System.out.println("0. 돌아가기");

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(String.format("%d. [%s] %s", i + 1, accounts.get(i).getBankName(),
                    accounts.get(i).getAccountNumberWithHypen()));
        }

        System.out.print("번호 입력 : ");
        String selectInput = selectScanner.next();

        for (int i = 0; i < selectInput.length(); i++) {
            char moveChar = selectInput.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showDeleteAccountUI(customer);
                return;
            }
        }

        int selectInt = Integer.parseInt(selectInput);

        if (selectInt < 0 || selectInt > accounts.size()) {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showDeleteAccountUI(customer);
            return;
        }

        if (selectInt == 0) {
            ViewMethod.jump();
            showMainAccountUI(customer);
            return;
        }

        if (AccountApp.unregisterAccount(customer.getCustomerId(), selectInt - 1)) {
            ViewMethod.jump();
            System.out.print("계좌 삭제중");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("showCreateAccountUI[line, 50] : InterruptException!");
                }
                System.out.print(".");
            }
            ViewMethod.jump();
            System.out.printf("계좌가 삭제되었습니다👍%s", System.lineSeparator(), System.lineSeparator());
            showMainAccountUI(customer);
        } else {
            ViewMethod.jump();
            System.out.println("😮계좌에 잔액이 남아 삭제할 수 없습니다");
            showDeleteAccountUI(customer);
        }
    }


}
