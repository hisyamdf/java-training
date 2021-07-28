package com.training.data.repos;

import com.training.data.entity.Product;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {
    
    //other function 
    
}