package com.ecommerce.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CategorieService;
import com.ecommerce.service.CustomUserDetailService;
import com.ecommerce.service.ProductService;

@RestController
public class AdminController {
	
	public static String uploadDir=System.getProperty("user.dir") +"/src/main/resources/static/productImages";
	
	@Autowired
	CategorieService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/admin")
	public ModelAndView index () {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("adminHome");
	    return modelAndView;
	}
	
	@GetMapping("/reset")
	public ModelAndView reset () {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("changePassword");
	    return modelAndView;
	}
	
	@PostMapping("/reset")
	public ModelAndView registerPost(@RequestParam("password") String password,HttpServletRequest request) throws ServletException
	{
		
		String email=request.getUserPrincipal().getName();
		User user=userRepository.findByEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(password));
		//userRepository.save(user);
		
		
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/admin/products");
	    return modelAndView;
	}
	
	
	
	@GetMapping("/admin/categories")
	public ModelAndView getCategories (Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("categories");
	    return modelAndView;
	}
	
	@GetMapping("/admin/categories/add")
	public ModelAndView  addCategories(Model model) {
		model.addAttribute("category",new Category());
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("categoriesAdd");
	    return modelAndView;
	}
	
	@PostMapping("/admin/categories/add")
	public ModelAndView saveCategories(@ModelAttribute("category") Category category) {
		
		categoryService.addCategory(category);
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/admin/categories");
	    return modelAndView;
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public ModelAndView  deleteCategory(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/admin/categories");
	    return modelAndView;
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public ModelAndView  updateCategory(@PathVariable int id,Model model) {
		Optional<Category> category=categoryService.getCategoryById(id);
		if(category.isPresent())
		{
			model.addAttribute("category",category.get());
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("categoriesAdd");
		    return modelAndView;
			
		}
		else {
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("adminHome");
		    return modelAndView; 
		}
	    
	}
	
	//Product Section
	@GetMapping("/admin/products")
	public ModelAndView  getProducts(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("products");
	    return modelAndView;
	}
	
	@GetMapping("/admin/products/add")
	public ModelAndView  addProduct(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("productsAdd");
	    return modelAndView;
	}
	@PostMapping("/admin/products/add")
	public ModelAndView productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,@RequestParam("productImage")MultipartFile file,@RequestParam("imgName")String imgName) throws IOException
	{
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		
		if(!file.isEmpty())
		{
			imageUUID=file.getOriginalFilename();
			
			Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else
		{
			imageUUID =imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProduct(product);
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/admin/products");
	    return modelAndView;
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public ModelAndView  deleteProduct(@PathVariable long id) {
		productService.removeProductById(id);
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/admin/products");
	    return modelAndView;
	}
	
	@GetMapping("/admin/product/update/{id}")
	public ModelAndView updateProduct(@PathVariable long id,Model model)
	{
		Product product=productService.getProductById(id).get();
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		model.addAttribute("categories", categoryService.getAllCategory());
		
		model.addAttribute("productDTO",productDTO);
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("productsAdd");
	    return modelAndView;
		
	}
	
	 @GetMapping( "/admin/users")
	   public ModelAndView shop(Model model,String keyword)
	   {
		 
		 if(keyword!=null)
		 {
			 model.addAttribute("users",customUserDetailService.getAllUsersByFirstName(keyword));
		 }
		 
		 else
		 {
			 model.addAttribute("users",customUserDetailService.getAllUsers());
		 }
		   
		   ModelAndView modelView =new ModelAndView();
		   modelView.setViewName("users");
		   return modelView;
	   }
	 
	 
	 @GetMapping( "/admin/orders")
	   public ModelAndView getOrders(Model model)
	   {
		 
		 
		 
		 
			 model.addAttribute("orders",customUserDetailService.getAllOrders());
		 
		   
		   ModelAndView modelView =new ModelAndView();
		   modelView.setViewName("orders");
		   return modelView;
	   }
	 

	
}
