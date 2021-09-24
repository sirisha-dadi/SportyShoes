package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.CustomUserDetail;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user=userRepository.findUserByEmail(email);
		
		user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		
		return user.map(CustomUserDetail::new).get();
		
	}
	
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	public List<User> getAllUsersByFirstName(String name)
	{
	   return userRepository.findUserByUsernameLike(name);
	}
	
	public List<Orders> getAllOrders()
	{
	   return orderRepository.findAll();
	}
	
	

}
