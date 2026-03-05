package org.example.moneyflowspring.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategorySummary {
    private Long categoryId;
    private String categoryName;
    private String categoryIcon;
    private String categoryColor;
    private boolean categoryIsPositive;
    private double totalAmount;
}
