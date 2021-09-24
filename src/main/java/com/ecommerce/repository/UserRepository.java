package com.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	
	Optional<User> findUserByEmail(String email);
	
	User findByEmail(String email);
	
	
	@Query(value="select * from users u where u.first_name like %:keyword%",nativeQuery=true)
	List<User> findUserByUsernameLike(@Param("keyword") String keyword);
	 
	 List<User> findByFirstName(String firstName);
	
}
