package com.training.data.repos;

import com.training.data.entity.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {

}
