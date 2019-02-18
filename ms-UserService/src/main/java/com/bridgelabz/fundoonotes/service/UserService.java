package com.bridgelabz.fundoonotes.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoonotes.model.UserDetails;

public interface UserService {

	UserDetails register(UserDetails user, HttpServletRequest request);
	
  UserDetails login(UserDetails user, HttpServletRequest request,HttpServletResponse response);
	
	UserDetails activateUser(String token, HttpServletRequest request);
	
	UserDetails update(String token, UserDetails user, HttpServletRequest request);
	
	UserDetails delete( String token,HttpServletRequest request);
}
