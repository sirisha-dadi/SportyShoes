package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;

public class UserDTO {

	
	private Integer id;
    

    private String firstName;
    
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
    
    public UserDTO() {
		
	}
    public UserDTO(UserDTO user) {
		super();
		this.id = user.getId();
		this.firstName =user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}


}
