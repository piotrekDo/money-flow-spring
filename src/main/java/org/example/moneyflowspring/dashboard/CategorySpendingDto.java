package org.example.moneyflowspring.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategorySpendingDto {
    private Long id;
    private String color;
    private String imageUrl;
    private String name;
    private String icon;
    private Boolean isPositive;
    private Double totalCurrentMonth;
    private Long transactionsCountCurrentMonth;
    private Double averageLastThreeMonths;
    private Double trendPercent;

}