package bank.view;

import bank.controller.Account;
import bank.controller.Bank;
import bank.controller.Customer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class BankingView {

    // 소유한 계좌 목록을 보여주는 뷰
    public static void showAccountListUI(Bank bank, Customer customer) {
        Scanner moveScanner = new Scanner(System.in);
        List<Account> accounts = customer.getCustomerAccounts();

        System.out.println("========================================");
        System.out.println("<원하시는 계좌를 선택해주세요>");
        System.out.println("0. 돌아가기");

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, accounts.get(i).getAccountNumberWithHypen()));
        }

        System.out.println(String.format("%d. 종료", accounts.size() + 1));
        System.out.print("번호 입력 : ");
        String inputMove = moveScanner.next();


        // 모두 숫자로 이루어져있는지 검사
        // 범위 안에 있는지 검사
        // 은행 업무 선택
        for (int i = 0; i < inputMove.length(); i++) {
            char moveChar = inputMove.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showAccountListUI(bank, customer);
                return;
            }
        }

        int moveInt = Integer.parseInt(inputMove);
        if (moveInt < 0 || moveInt > accounts.size()) {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showAccountListUI(bank, customer);
            return;
        }

        if (moveInt == 0) {
            ViewMethod.jump();
            AccountView.showMainAccountUI(bank, customer);
        } else if (moveInt == accounts.size() + 1) {
            ViewMethod.jump();
            ViewMethod.printExitMessage();
            System.exit(0);
        } else {
            ViewMethod.jump();
            showBankingUI(bank, customer, moveInt);
        }
    }

    // 은행 업무를 선택하게 되는 뷰
    public static void showBankingUI(Bank bank, Customer customer, int index) {
        Scanner moveScanner = new Scanner(System.in);
        index -= 1;
        System.out.println("========================================");
        System.out.println("<원하시는 업무를 선택해주세요>");
        System.out.println(String.format("<현재 계좌 : %s>", customer.getAccount(index).getAccountNumberWithHypen()));
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
                showAccountListUI(bank, customer);
                return;
            }
        }

        int moveInt = Integer.parseInt(inputMove);
        switch (moveInt) {
            case 0:
                ViewMethod.jump();
                showAccountListUI(bank, customer);
                break;
            case 1:
                ViewMethod.jump();
                showDepositUI(bank, customer, index);
                break;
            case 2:
                ViewMethod.jump();
                showWithdrawUI(bank, customer, index);
                break;
            case 3:
                ViewMethod.jump();
                showTransferUI(bank, customer, index);
                break;
            case 4:
                ViewMethod.jump();
                System.out.println("----------------------------------------");
                System.out.println(String.format("💰잔고: %s원", customer.getAccount(index).getBalance()));
                showBankingUI(bank, customer, index + 1);
                break;
            case 5:
                ViewMethod.jump();
                showHistoriesUI(bank, customer, index);
                break;
            case 6:
                ViewMethod.jump();
                ViewMethod.printExitMessage();
                System.exit(0);
                break;
            default:
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showBankingUI(bank, customer, index + 1);
                break;
        }
    }

    // 입금
    public static void showDepositUI(Bank bank, Customer customer, int index) {
        Scanner amountScanner = new Scanner(System.in);
        DecimalFormat decimalFormatter = new DecimalFormat("0.##");
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print(String.format(" 입금할 금액(이율이 %s 입니다.) : ", decimalFormatter.format(customer.getAccount(index).getInterestRate())));
        String inputAmount = amountScanner.next();

        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showDepositUI(bank, customer, index);
                return;
            }
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            showBankingUI(bank, customer, index + 1);
            return;
        }

        Account account = customer.getAccount(index);
        BigDecimal afterAmount = account.deposit(amount);
        ViewMethod.jump();
        System.out.println(String.format("💸입금 완료: +%s원", afterAmount));
        System.out.println(String.format("💰잔고: %s원", account.getBalance()));
        showBankingUI(bank, customer, index + 1);
    }

    // 출금
    public static void showWithdrawUI(Bank bank, Customer customer, int index) {
        Account account = customer.getAccount(index);

        Scanner amountScanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.println(String.format("<💰현재 잔고: %s원>", account.getBalance()));
        System.out.print("출금할 금액 : ");
        String inputAmount = amountScanner.next();

        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showWithdrawUI(bank, customer, index);
                return;
            }
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            showBankingUI(bank, customer, index + 1);
            return;
        }

        BigDecimal afterAmount = account.withdraw(amount);
        if (afterAmount.compareTo(BigDecimal.ZERO) == 0) {
            ViewMethod.jump();
            System.out.println("잔액이 부족합니다😮");
            showBankingUI(bank, customer, index + 1);
            return;
        }

        ViewMethod.jump();
        System.out.println(String.format("💸출금 완료: -%s원", afterAmount));
        System.out.println(String.format("💰잔고: %s원", account.getBalance()));
        showBankingUI(bank, customer, index + 1);
    }

    // 모든 거래내역을 보는 뷰
    public static void showHistoriesUI(Bank bank, Customer customer, int index) {
        Scanner moveScanner = new Scanner(System.in);
        Account account = customer.getAccount(index);

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
                showHistoriesUI(bank, customer, index);
                return;
            }
        }

        int moveInt = Integer.parseInt(move);
        if (moveInt == 48) {
            ViewMethod.jump();
            showBankingUI(bank, customer, index + 1);
            System.exit(0);
        } else if (moveInt < 0 ||moveInt > account.getHistories().size()) {
            ViewMethod.jump();
            ViewMethod.printWrongTypingMessage();
            showHistoriesUI(bank, customer, index);
        } else {
            ViewMethod.jump();
            showHistory(bank, customer, index, Integer.parseInt(move));
        }
    }

    // 특정 거래내역을 상세로 보는 뷰
    public static void showHistory(Bank bank, Customer customer, int accountIndex, int historyIndex) {
        Scanner moveScanner = new Scanner(System.in);
        Account account = customer.getAccount(accountIndex);
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.println("----------------------------------------");
        System.out.println(String.format("%s%s", account.printHistory(--historyIndex), System.lineSeparator()));
        System.out.println("----------------------------------------");
        System.out.print("번호 입력 : ");
        String move = moveScanner.next();
        // 입력 받은 문자열이 0인지 확인!
        for (int i = 0; i < move.length(); i++) {
            char moveChar = move.charAt(i);
            if (moveChar != 48) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showHistory(bank, customer, accountIndex, historyIndex + 1);
            } else {
                ViewMethod.jump();
                showHistoriesUI(bank, customer, accountIndex);
            }
        }
    }

    public static void showTransferUI(Bank bank, Customer customer, int index) {
        Account account = customer.getAccount(index);

        Scanner amountScanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.println(String.format("<💰현재 잔고: %s원>", account.getBalance()));
        System.out.print("송금할 계좌번호를 입력해주세요. : ");
        String inputAccount = amountScanner.next();
        Account yourAccount = bank.findAccountOrNull(inputAccount);
        if (yourAccount == null) {
            ViewMethod.jump();
            System.out.println("계좌를 찾을 수 없었습니다🥲");
            showTransferUI(bank, customer, index);
        }
        System.out.print("송금할 금액 : ");

        String inputAmount = amountScanner.next();
        for (int i = 0; i < inputAmount.length(); i++) {
            char moveChar = inputAmount.charAt(i);
            if (moveChar < 48 || moveChar > 57) {
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showTransferUI(bank, customer, index);
                return;
            }
        }

        BigDecimal amount = new BigDecimal(inputAmount);
        if(account.sendMoney(yourAccount, amount)) {
            ViewMethod.jump();
            System.out.println(String.format("💸%s 님께 송금완료!", yourAccount.getOwnerName()));
            System.out.println(String.format("💰잔고: %s원", account.getBalance()));
            showBankingUI(bank, customer, ++index);
        } else {
            ViewMethod.jump();
            System.out.println("잔액이 부족합니다😮");
            showTransferUI(bank, customer, index);
        }
    }
}

