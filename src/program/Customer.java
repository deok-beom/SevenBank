package B7Bank;

import java.util.HashMap;
//- 정보(멤버 변수): 이름, 소유 계좌
//- 동작(함수): 소유 계좌 조회
public class Customer {  //고객클래스
    private String name;  //고객이름
    private HashMap<String, String> accounts; //고객의 계좌를 해시맵구조로 고객의 정보? < String name : String  account> 담는 accounts ???
    private String customerId; // 동명 이인일 수 있어서 이걸로 식별

    public Customer(String name, String customerId) {
        this.name = name;
        this.accounts = new HashMap<>();
        this.customerId = customerId;
    }

}
