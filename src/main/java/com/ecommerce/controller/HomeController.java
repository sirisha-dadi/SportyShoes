package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.CategorieService;
import com.ecommerce.service.ProductService;

@Controller
public class HomeController {
  @Autowired
  CategorieService categoryService;
  
  @Autowired
  ProductService productService;
  
   @GetMapping({"/", "/home"})
   public ModelAndView home(Model model)
   {
	   model.addAttribute("cartCount",GlobalData.cart.size());
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("index");
	   return modelView;
   }
   
   @GetMapping( "/shop")
   public ModelAndView shop(Model model)
   {
	   model.addAttribute("cartCount",GlobalData.cart.size());
	   model.addAttribute("categories",categoryService.getAllCategory());
	   model.addAttribute("products",productService.getAllProduct());
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("shop");
	   return modelView;
   }
   @GetMapping("/shop/category/{id}")
   public ModelAndView shopByCategory(Model model,@PathVariable int id)
   {
	   model.addAttribute("cartCount",GlobalData.cart.size());
	   model.addAttribute("categories",categoryService.getAllCategory());
	   model.addAttribute("products",productService.getAllproductsByCategoryId(id));
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("shop");
	   return modelView;
   }
   
   @GetMapping("/shop/viewproduct/{id}")
   public ModelAndView viewProduct(Model model,@PathVariable int id)
   {
	   
	   model.addAttribute("product",productService.getProductById(id).get());
	   model.addAttribute("cartCount",GlobalData.cart.size());
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("viewProduct");
	   return modelView;
   }
   
   @GetMapping("/cart/removeItem/{Index}")
   public ModelAndView cartItemRemove(@PathVariable int index)
   {
	   
	   GlobalData.cart.remove(index);
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("redirect:/cart");
	   return modelView;
   }
   
   @GetMapping("/checkout")
   public ModelAndView checkout(Model model)
   {
	   
	   User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
	   ModelAndView modelView =new ModelAndView();
	   modelView.setViewName("checkout");
	   return modelView;
   }
}
