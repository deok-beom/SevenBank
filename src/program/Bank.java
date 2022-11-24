package B7Bank;

import java.util.List;
import java.util.ArrayList;

//- 정보(멤버 변수): 계좌들, 고객들, 이름
// - 동작(함수): 계좌 수정/삭제/등록, 고객 등록, 계좌 번호로 계좌 조회, 소유자 명으로 계좌 조회, 모든 계좌 목록 조회
public class  Bank {
    private String bankName; //은행이름
    private List<Account> accounts;  //계좌를 리스트 구조로 담는 계좌리스트 변수인 accounts
    private List<Customer> customers; //고객을 리스트 구조로 담는 고객리스트 변수인 customer


    public Bank(String bankName) {  //생성자
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }


}
