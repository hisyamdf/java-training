package com.training.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class ProductData {

    @NotEmpty(message = "Code is required")
    @Size(min=3, max=5, message="Code length must be 3 to 5 characters")
    @Pattern(regexp = "A[0-9]+", message = "Code must be start with PK")
    private String code;

    @NotEmpty(message = "Name is required")
    private String name;
    private double price;
    private String description;
    private Long categoryId;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


}
