package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class FinancialTransactionFileRecord {
    private TranType tranType;
    private String tranDate;
    private String tranNr;
    private String accountNr;
    private String merchantData;
    private String title;
    private Double amount;

    @Override
    public String toString() {
        return "FinancialTransactionFileRecord{" +
                ", tranType=" + tranType +
                ", tranDate=" + tranDate +
                ", tranNr='" + tranNr + '\'' +
                ", accountNr='" + accountNr + '\'' +
                ", merchantData='" + merchantData + '\'' +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                '}';
    }
}
