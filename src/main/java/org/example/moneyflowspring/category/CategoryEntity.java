package org.example.moneyflowspring.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    private String icon;
    private String color;
    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SubcategoryEntity> categoryEntities;
}
