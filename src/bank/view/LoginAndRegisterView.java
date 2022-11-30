package bank.view;

import bank.application.CustomerApp;
import bank.entity.Customer;

import java.io.Console;
import java.util.Scanner;

public class LoginAndRegisterView {

    public static void printLogo() { // main 이 시작될 때 한번만 불러준다.
        System.out.println("███████╗███████╗██╗   ██╗███████╗███╗   ██╗    ██████╗  █████╗ ███╗   ██╗██╗  ██╗");
        System.out.println("██╔════╝██╔════╝██║   ██║██╔════╝████╗  ██║    ██╔══██╗██╔══██╗████╗  ██║██║ ██╔╝");
        System.out.println("███████╗█████╗  ██║   ██║█████╗  ██╔██╗ ██║    ██████╔╝███████║██╔██╗ ██║█████╔╝");
        System.out.println("╚════██║██╔══╝  ╚██╗ ██╔╝██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══██║██║╚██╗██║██╔═██╗");
        System.out.println("███████║███████╗ ╚████╔╝ ███████╗██║ ╚████║    ██████╔╝██║  ██║██║ ╚████║██║  ██╗");
        System.out.println("╚══════╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝");
    }

    public static void showBeginningUI() { // 초기 시작화면을 보여주는 UI
        Scanner inputSc = new Scanner(System.in);

        System.out.println("********** Seven Bank 에 오신걸 환영합니다. **********");
        System.out.println("<원하시는 기능을 선택해주세요>");
        System.out.println("1. 로그인");
        System.out.println("2. 회원가입");
        System.out.println("3. 종료");
        System.out.print("번호 입력 : ");

        String num = inputSc.next();

        switch (num) {
            case "1":
                ViewMethod.jump();
                showLoginUI();
                break;
            case "2":
                ViewMethod.jump();
                showRegisterCustomerUI();
                break;
            case "3":
                ViewMethod.jump();
                ViewMethod.printExitMessage();
                System.exit(0);
                break;
            default:
                ViewMethod.jump();
                ViewMethod.printWrongTypingMessage();
                showBeginningUI();
                break;
        }
    }

    public static void showLoginUI() {
        Scanner inputSc = new Scanner(System.in);

        System.out.println("========== 로그인 페이지 입니다. ==========");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print("ID : ");
        String customerID = inputSc.next();

        if(customerID.equals("0")) {
            ViewMethod.jump();
            showBeginningUI();
        }

        Customer customer = CustomerApp.getCustomerOrNull(customerID);
        if (customer == null) {
            ViewMethod.jump();
            System.out.println("존재하지 않는 아이디입니다😮");
            showLoginUI();
        }

        System.out.print("PASSWORD : ");
        String password = inputSc.next();

        if(password.equals("0")) {
            ViewMethod.jump();
            showBeginningUI();
        }

        if (CustomerApp.loginCustomer(customer, password)) {
            ViewMethod.jump();
            System.out.println("로그인 되었습니다😉");
            AccountView.showMainAccountUI(customer);
        } else {
            ViewMethod.jump();
            System.out.println("비밀번호가 틀립니다😓");
            showLoginUI();
        }
    }

    public static void showRegisterCustomerUI() {
        Scanner inputSc = new Scanner(System.in);

        System.out.println("========== 회원가입 페이지 입니다 ==========");
        System.out.println("<되돌아 가려면 0번을 입력하세요.>");
        System.out.print("ID : ");
        String newCustomerID = inputSc.next();

        if(newCustomerID.equals("0")) {
            ViewMethod.jump();
            showBeginningUI();
            return;
        }

        if (CustomerApp.checkDuplicateID(newCustomerID)) {
            System.out.print("PASSWORD : ");
            String newCustomerPassword = inputSc.next();
            if(newCustomerPassword.equals("0")) {
                ViewMethod.jump();
                showBeginningUI();
                return;
            }

            System.out.print("NAME : ");
            String customerName = inputSc.next();

            if(customerName.equals("0")) {
                ViewMethod.jump();
                showBeginningUI();
                return;
            }

            CustomerApp.registerCustomer(newCustomerID, String.valueOf(newCustomerPassword), customerName);
        } else {
            ViewMethod.jump();
            System.out.println("중복된 ID 입니다🥲");
            System.out.println("다시 입력해주세요.");
            showRegisterCustomerUI();
            return;
        }

        ViewMethod.jump();
        System.out.println("회원가입이 완료되었습니다!!😊");
        showLoginUI();
    }
}
