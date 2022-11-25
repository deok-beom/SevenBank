package bank.view;

import bank.controller.Account;

import java.util.List;
import java.util.Scanner;

public class BankingView {

    Scanner inputSc = new Scanner(System.in);

    // UI 설계시
    public void UiShowAmount(List<Account> accounts ) {
        System.out.println("--------------------------------");
        System.out.println("1. 입금 ｜2. 출금 ｜ 3. 잔여금 ");
        System.out.print(" > "); //어떤 작업을 할지 번호 기입란 표기   예   > 1
        String inputNum = inputSc.next(); //메뉴1,2,3 중 입력된 값 input에 저장, String형 숫자 기입
//        System.out.println("고객명을 입력하세요");
//        String inputName = inputSc.next();
        System.out.println("계좌번호를 입력하세요");
        String inputAccount = inputSc.next();


        for (int i = 0; i < accounts.size(); i++) {  //accounts =>계좌리스트
            if (!accounts.get(i).getAccountNumber().equals(inputAccount)) {
                System.out.println("잘못된 입력 입니다.");
                //return  되돌아가기 기능 구현
            } else {

                switch (inputNum) {
                    case "1":
                        System.out.println(" 입금 : " + "입금금액변수 " + "원");
                        break;

                    case "2":
                        System.out.println(" 출금 : " + "출금금액변수" + "원");
                        break;

                    case "3":
                        System.out.println(" 잔고 : " + "잔고금액변수" + "원");
                        break;
                }
            }

        }
//        1-1 고객에게 보려고 하는 [1.입금/2.출금/3.잔여금]의 원하는 서비스 번호를 요청한다.
//        1-2 고객이 번호를 입력한다  ( 스캐너 값을 변수에 저장)
//        1-3 고객에게 이름과 계좌번호를 입력하도록 요청한다.
////        1-4  고객이 이름과 계좌번호를 입력한다
//            1-5-1 if (입력된 고객의 이름과 계좌번호가 계좌리스트에 불일치시 ){
//        다시 되돌아기기 또는 알림메세지”고객명이 일치하지 않습니다.” 라고 출력
//    }else { 고객에게 해당 번호의 금액을 조회 → 출력 → 확인시켜준다. (switch 문)
//        {
//            1-6 else 속에 switch 문 구현 하여 고객에게 해당번호의 금액 조회
    }
}