package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategorieService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	//gets all existing categories
	public List<Category> getAllCategory()
	{
		return categoryRepository.findAll();
	}
	
	// method to add new categories
	public void addCategory(Category category)
	{
		categoryRepository.save(category);
	}
	
	public void removeCategoryById(int id)
	{
		categoryRepository.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id)
	{
		return categoryRepository.findById(id);
	}

}
