package com.example.ostadmart.models;

import lombok.Data;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Local Imports
import com.example.ostadmart.enums.Category;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", indexes = {
        @Index(name = "idx_product_category", columnList = "category")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "description", length = 512, nullable = false, columnDefinition = "varchar(512) default ''")
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity_left", nullable = false)
    private Integer quantityLeft;

    @Column(name = "product_photo", nullable = false)
    private String productPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = true)
    private User updatedBy;

}
