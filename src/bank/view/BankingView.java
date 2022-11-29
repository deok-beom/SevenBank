package bank.view;

import bank.application.AccountApp;
import bank.entity.Account;
import bank.entity.Customer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import static bank.view.AccountView.showMainAccountUI;

public class BankingView {

    // 소유한 계좌 목록을 보여주는 뷰
    public static void showAccountListUI(Customer customer) {
        Scanner moveScanner = new Scanner(System.in);
        List<Account> accounts = customer.getCustomerAccounts();
        if (accounts.size() == 0) {
            ViewMethod.jump();
            System.out.println("👀선택할 수 있는 계좌가 없습니다");
            showMainAccountUI(customer);
        }

        System.out.println("========================================");
        System.out.println("<원하시는 계좌를 선택해주세요>");
        System.out.println("0. 돌아가기");

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(String.format("%d. [%s] %s", i + 1, accounts.get(i).getBankName(),
                    accounts.get(i).getAccountNumberWithHypen()));
        }

        System.out.println(String.format("%d. 종료", accounts.size() + 1));
        System.out.print("번호 입력 : ");
        String inputMove = moveScanner.next();



        for (int i = 0; i < inputMove.length(); i++) { //입력한 문자가 모두 숫자로 이루어졌는지 검사
            char moveChar = inputMove.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showAccountListUI(customer);
                return;
            }
        }

        int moveInt = Integer.parseInt(inputMove);
        if (moveInt < 0 || moveInt > accounts.size() + 1) { // 범위 안에 있는지 검사
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showAccountListUI(customer);
            return;
        }

        if (moveInt == 0) {  // 은행 업무 선택
            ViewMethod.jump();
            showMainAccountUI(customer);
        } else if (moveInt == accounts.size() + 1) {
            ViewMethod.jump();
            ViewMethod.printExitMessage();
            System.exit(0);
        } else {
            ViewMethod.jump();
            showBankingUI(customer, moveInt);
        }
    }

    // 은행 업무를 선택하게 되는 뷰
    public static void showBankingUI(Customer customer, int index) {
        Scanner moveScanner = new Scanner(System.in);
        Account currentAccount = customer.getAccount(index - 1);
        System.out.println(String.format("👛현재 계좌 : [%s] %s", currentAccount.getBankName(),
                currentAccount.getAccountNumberWithHypen()));
        System.out.println("========================================");
        System.out.println("<원하시는 업무를 선택해주세요>");
        System.out.println("0. 돌아가기");
        System.out.println("1. 입금");
        System.out.println("2. 출금");
        System.out.println("3. 송금");
        System.out.println("4. 잔고 확인");
        System.out.println("5. 거래내역 확인");
        System.out.println("6. 종료");
        System.out.print("번호 입력 : ");
        String inputMove = moveScanner.next();

        for (int i = 0; i < inputMove.length(); i++) {
            char moveChar = inputMove.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showAccountListUI(customer);
                return;
            }
        }

        int moveInt = Integer.parseInt(inputMove);
        switch (moveInt) {
            case 0:
                ViewMethod.jump();
                showAccountListUI(customer);
                break;
            case 1:
                ViewMethod.jump();
                showDepositUI(customer, index);
                break;
            case 2:
                ViewMethod.jump();
                showWithdrawUI(customer, index);
                break;
            case 3:
                ViewMethod.jump();
                showTransferUI(customer, index);
                showBankingUI(customer, index);
                break;
            case 4:
                ViewMethod.jump();
                System.out.print(String.format("%s💰잔고: %s원", System.lineSeparator(), currentAccount.getBalance()));
                System.out.println(String.format("%s💰연이율이 적용된 예상 잔고: %s원", System.lineSeparator(),
                        currentAccount.getBalanceApplyInterestRate()));
                showBankingUI(customer, index);
                break;
            case 5:
                ViewMethod.jump();
                showHistoriesUI(customer, index);
                break;
            case 6:
                ViewMethod.jump();
                ViewMethod.printExitMessage();
                System.exit(0);
                break;
            default:
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showBankingUI(customer, index);
                break;
        }
    }

    // 입금
    public static void showDepositUI(Customer customer, int index) {
        Account account = customer.getAccount(index - 1);

        Scanner amountScanner = new Scanner(System.in);
        DecimalFormat decimalFormatter = new DecimalFormat("0.##");
        System.out.println(String.format("👛현재 계좌 : [%s] %s", account.getBankName(),
                account.getAccountNumberWithHypen()));
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print(String.format(" 입금할 금액(이율이 %s%% 입니다.) : ",
                decimalFormatter.format(account.getInterestRate().multiply(BigDecimal.valueOf(100)))));
        String inputAmount = amountScanner.next();

        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showDepositUI(customer, index);
                return;
            }
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            showBankingUI(customer, index);
            return;
        }


        BigDecimal afterAmount = account.deposit(amount);
        ViewMethod.jump();
        System.out.println(String.format("💸입금 완료: +%s원", afterAmount));
        System.out.println(String.format("💰잔고: %s원", account.getBalance()));
        showBankingUI(customer, index);
    }

    // 출금
    public static void showWithdrawUI(Customer customer, int index) {
        Account account = customer.getAccount(index - 1);

        Scanner amountScanner = new Scanner(System.in);
        System.out.println(String.format("💰현재 잔고: %s원", account.getBalance()));
        System.out.println(String.format("👛현재 계좌 : [%s] %s", account.getBankName(),
                account.getAccountNumberWithHypen()));
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print("출금할 금액 : ");
        String inputAmount = amountScanner.next();

        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showWithdrawUI(customer, index);
                return;
            }
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            showBankingUI(customer, index);
            return;
        }

        BigDecimal afterAmount = account.withdraw(amount);
        if (afterAmount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            System.out.println("잔액이 부족합니다😮");
            showBankingUI(customer, index);
            return;
        }

        ViewMethod.jump();
        System.out.println(String.format("💸출금 완료: -%s원", afterAmount));
        System.out.println(String.format("💰잔고: %s원", account.getBalance()));
        showBankingUI(customer, index);
    }

    // 송금
    public static void showTransferUI(Customer customer, int index) {
        Account account = customer.getAccount(index - 1);

        Scanner amountScanner = new Scanner(System.in);
        System.out.println(String.format("💰현재 잔고: %s원", account.getBalance()));
        System.out.println(String.format("👛현재 계좌 : [%s] %s", account.getBankName(),
                account.getAccountNumberWithHypen()));
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print("송금할 계좌번호를 입력해주세요. : ");
        String inputAccount = amountScanner.next();
        Account yourAccount = AccountApp.getAccountByBankNumberOrNull(inputAccount);

        if (inputAccount.equals("0")) {
            ViewMethod.jump();
            showBankingUI(customer, index);
        }

        if (yourAccount == null) {
            ViewMethod.jump();
            System.out.println("계좌를 찾을 수 없습니다🥲");
            showTransferUI(customer, index);
            return;
        }

        BigDecimal fee = BigDecimal.ZERO;
        if (account.getBankName().equals(yourAccount.getBankName())) {
            System.out.print("송금할 금액 : ");
        } else {
            System.out.print("타행 이체 수수료는 500원입니다! ");
            System.out.print("송금할 금액 : ");
            fee = BigDecimal.valueOf(500);
        }

        String inputAmount = amountScanner.next();
        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showTransferUI(customer, index);
                return;
            }
        }

        if (inputAmount.equals("0")) {
            ViewMethod.jump();
            showBankingUI(customer, index);
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if (account.transfer(yourAccount, amount, fee)) {
            ViewMethod.jump();
            System.out.println(String.format("💸%s 님께 송금완료!", yourAccount.getOwnerName()));
            System.out.println(String.format("💰잔고: %s원", account.getBalance()));
            showBankingUI(customer, index);
        } else {
            ViewMethod.jump();
            System.out.println("잔액이 부족합니다😮");
            showTransferUI(customer, index);
        }
    }

    // 모든 거래내역을 보는 뷰
    public static void showHistoriesUI(Customer customer, int index) {
        Account account = customer.getAccount(index - 1);
        if (account.printAllHistoriesOrNull() == null) {
            ViewMethod.jump();
            System.out.println("👀거래내역이 텅 비었습니다");
            showBankingUI(customer, index);
        }

        Scanner moveScanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print(account.printAllHistoriesOrNull());
        System.out.print("번호 입력 : ");
        String move = moveScanner.next();

        // 입력 받은 문자열이 숫자가 맞는지 확인!
        for (int i = 0; i < move.length(); i++) {
            char moveChar = move.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showHistoriesUI(customer, index);
                return;
            }
        }

        int moveInt = Integer.parseInt(move);
        if (moveInt == 0) {
            ViewMethod.jump();
            showBankingUI(customer, index);
        } else if (moveInt < 0 || moveInt > account.getHistories().size()) {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showHistoriesUI(customer, index);
        } else {
            ViewMethod.jump();
            showHistory(customer, index, Integer.parseInt(move));
        }
    }

    // 특정 거래내역을 상세로 보는 뷰
    public static void showHistory(Customer customer, int accountIndex, int historyIndex) {
        Scanner moveScanner = new Scanner(System.in);
        Account account = customer.getAccount(accountIndex - 1);

        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.println("----------------------------------------");
        System.out.println(account.printHistory(historyIndex - 1));
        System.out.println("----------------------------------------");
        System.out.print("번호 입력 : ");
        String move = moveScanner.next();
        // 입력 받은 문자열이 0인지 확인!
        for (int i = 0; i < move.length(); i++) {
            char moveChar = move.charAt(i);
            if (moveChar != 48) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showHistory(customer, accountIndex, historyIndex);
            } else {
                ViewMethod.jump();
                showHistoriesUI(customer, accountIndex);
            }
        }
    }
}

