package bank.entity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class History {
    private OffsetDateTime transactionDate;
    private String accountNumber;
    private ETradeType type;
    private BigDecimal amount;
    private BigDecimal fee;
    private BigDecimal afterBalance;
    private String receiverName;

    public History(OffsetDateTime transactionDate, String accountNumber, ETradeType type, BigDecimal amount, BigDecimal fee, BigDecimal afterBalance, String receiverName) {
        this.transactionDate = transactionDate;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.fee = fee;
        this.afterBalance = afterBalance;
        this.receiverName = receiverName;
    }

    public String getTransactionDate() {
        return this.transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    public ETradeType getType() {
        return type;
    }

    // 거래 유형을 String으로 반환해주는 메소드
    public String getTypeByString() {
        String type = "오류";
        switch (this.type) {
            case DEPOSIT:
                type = "입금";
                break;
            case WITHDRAW:
                type = "출금";
                break;
            case TRANSFER:
                if (fee.compareTo(BigDecimal.ZERO) > 0) {
                    type = "타행 송금";
                } else {
                    type = "송금";
                }
                break;
            default:
                assert (false);
                break;
        }

        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFee() { return fee; }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public String getReceiverNameName() {
        return receiverName;
    }
}
