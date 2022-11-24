package B7Bank;

import java.time.OffsetDateTime;
//정보(멤버 변수): 거래 일자, 거래 시간, 계좌번호, 입금/출금 여부, 거래 금액, 은행 명, 입금자
//변수 중 은행명은?
public class History {  //거래내역
    private OffsetDateTime transactionDate; //거래일자
    private String accountNumber; //계좌번호
    private boolean type; // True: 입금, False: 출금
    private int amount; // 거래금액, 마찬가지로 int로 했지만 후에 Decimal 등 적절한 형태로 변환
    private String traderName; //거래자이름

    public History(OffsetDateTime transactionDate, String accountNumber, boolean type, int amount, String traderName) {
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.traderName = traderName;
    }


}
