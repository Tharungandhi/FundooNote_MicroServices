package com.bridgelabz.fundoonotes.service;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dao.UserDetailsRepository;
import com.bridgelabz.fundoonotes.model.UserDetails;
import com.bridgelabz.fundoonotes.util.EmailUtil;
import com.bridgelabz.fundoonotes.util.GenerateTokenImlp;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl  implements UserService {

	   @Autowired
	    private UserDetailsRepository userDetailsRepository;
	   
	   @Autowired
		private PasswordEncoder bcryptEncoder;
	   
	   @Autowired
		private GenerateTokenImlp generateToken;
	   
	   @Autowired
		private EmailUtil emailutil;

	   private static Logger log = org.slf4j.LoggerFactory	.getLogger(UserServiceImpl.class);

	public UserDetails register(UserDetails user, HttpServletRequest request) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		UserDetails newUser = userDetailsRepository.save(user);
		String token = generateToken.generateToken(String.valueOf(newUser.getId()));
		StringBuffer requestUrl = request.getRequestURL();
		String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
		activationUrl += "/activationstatus/" + token;
		emailutil.sendEmail("", "Activation Status Verification", activationUrl);
		return newUser;

	}
	
	   
	public String login(UserDetails user, HttpServletRequest request,HttpServletResponse response) 
	{
		UserDetails existingUser=userDetailsRepository.getUserByEmailId(user.getEmailId());
		
		if (bcryptEncoder.matches(user.getPassword(),existingUser.getPassword() ) && existingUser.isActivationStatus()) {
			String token = generateToken.generateToken(String.valueOf(existingUser.getId()));
			log.info(token);
			return token;
		}
		
		
		return null;
	}
	
	public UserDetails activateUser(String token, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		Optional<UserDetails> maybeUser = userDetailsRepository.findById(id);
		return maybeUser
				.map(user -> userDetailsRepository.save(user.setActivationStatus(true)))
				.orElseGet(() -> null);
	}
    
    public UserDetails update(String token, UserDetails user, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		Optional<UserDetails> maybeUser = userDetailsRepository.findById(id);
		
		 return maybeUser
				.map(existingUser ->{
					existingUser.setName(user.getName())
			.setEmailId(user.getEmailId())
			.setMobileNumber(user.getMobileNumber())
			.setPassword(bcryptEncoder.encode(user.getPassword()));
			return userDetailsRepository.save(existingUser);	
    }).orElseGet(()-> null);
	}
    
	public boolean delete(String token, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		Optional<UserDetails> optional = userDetailsRepository.findById(id);
		return optional.map(user -> {
			userDetailsRepository.delete(user);
			return true;
		}).orElseGet(() -> false);
	}
    

	
	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		UserDetails user = userDetailsRepository.getUserByEmailId(emailId);
		if (user != null) {
			String token = generateToken.generateToken(String.valueOf(user.getId()));
			 StringBuffer requestUrl = request.getRequestURL();
	            String forgotPasswordLink = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
	            forgotPasswordLink = forgotPasswordLink + "/resetpassword/" + token;
	            emailutil.sendEmail("", "Rest password verification", forgotPasswordLink);
			
		}
		return false;

	}
	public UserDetails resetPassword(UserDetails user, String token, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		Optional<UserDetails> optional = userDetailsRepository.findById(id);
		if (optional.isPresent()) {
			UserDetails reSetUser=optional.get();
			reSetUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			userDetailsRepository.save(reSetUser);
			return reSetUser;
		}
		return null;
	}
    
}
