package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.UserDetails;

public interface UserService {

	UserDetails register(UserDetails user, HttpServletRequest request);
	
  String login(UserDetails user, HttpServletRequest request,HttpServletResponse response);
	
	UserDetails activateUser(String token, HttpServletRequest request);
	
	UserDetails update(String token, UserDetails user, HttpServletRequest request);
	
	boolean delete( String token,HttpServletRequest request);
	
	
	
	boolean forgotPassword(UserDetails user, HttpServletRequest request);
	
	UserDetails resetPassword(UserDetails user, String token, HttpServletRequest request);
	
    UserDetails uploadImage(String token, MultipartFile uploadData) throws IOException;
    
    UserDetails getImage(String token);
    
    UserDetails deleteFile(String token);

    void sendEmail(HttpServletRequest request, UserDetails user, String domainUrl, String message);
    
    UserDetails colaborator(String token, HttpServletRequest request);
}
