package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ecommerce.global.GlobalData;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;

@Controller
public class LoginController {
   
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public ModelAndView login()
	{
		GlobalData.cart.clear();
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("login");
	    return modelAndView;
	}
	@GetMapping("/register")
	public ModelAndView registerGet()
	{
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("register");
	    return modelAndView;
	}
	
	@PostMapping("/register")
	public ModelAndView registerPost(@ModelAttribute("user") User user,HttpServletRequest request) throws ServletException
	{
		String password =user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/");
	    return modelAndView;
	}
}
