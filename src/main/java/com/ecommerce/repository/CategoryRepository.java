package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Category;

//JapRepository provides predefined methods for saving data on database

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
