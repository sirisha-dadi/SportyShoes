package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@Controller
public class CartController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/addToCart/{id}")
	
	public ModelAndView addToCart(@PathVariable int id)
	{
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("redirect:/shop");
		GlobalData.cart.add(productService.getProductById(id).get());
		return modelAndView;
	}
	
@GetMapping("/cart")
	
	public ModelAndView cartGet(Model model)
	{
	
	   model.addAttribute("cartCount",GlobalData.cart.size());
	   model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
	   model.addAttribute("cart",GlobalData.cart);
	   
	   ModelAndView modelAndView=new ModelAndView();
	   modelAndView.setViewName("cart");
		
		return modelAndView;
	}

}
