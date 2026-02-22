package org.example.moneyflowspring.financial_transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SetTransactionCommentDto {
    private Long tranSystemId;
    private String comment;
}
