package org.example.moneyflowspring.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class TransactionWithCategory {
    private Long systemId;
    private Short tranType;
    private LocalDate tranDate;
    private Double amount;
    private String comment;
    private String merchantDataRaw;
    private String titleRaw;

    private Long categoryId;
    private String categoryName;
    private String categoryIcon;
    private String categoryColor;
    private boolean categoryIsPositive;

    private Long subcategoryId;
    private String subcategoryName;
    private String subcategoryIcon;
    private String subcategoryColor;

    private Long merchantId;
    private String imageUrl;
    private String merchantCode;
    private String merchantName;
}
