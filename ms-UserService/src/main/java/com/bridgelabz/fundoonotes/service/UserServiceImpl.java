package com.bridgelabz.fundoonotes.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	   
	   @Transactional
    public UserDetails register(UserDetails user, HttpServletRequest request) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		UserDetails newUser=  userDetailsRepository.save(user);
		String token = generateToken.generateToken(String.valueOf(newUser.getId()));
		 StringBuffer requestUrl = request.getRequestURL();
         String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
         activationUrl = activationUrl + "/activationstatus/" + token;
         System.out.println(activationUrl);
         emailutil.sendEmail("", "Activation Status Verification", activationUrl);
		return newUser;
	
	}
	
	   @Transactional
	public UserDetails login(UserDetails user, HttpServletRequest request,HttpServletResponse response) 
	{
		UserDetails existingUser=userDetailsRepository.getUserByEmailId(user.getEmailId());
		if (bcryptEncoder.matches(user.getPassword(),existingUser.getPassword() ) && existingUser.isActivationStatus() == true) {
			String token = generateToken.generateToken(String.valueOf(existingUser.getId()));
			response.setHeader("Tokens", token);
			return existingUser;
		}
		return existingUser;
		
	}
	
    public UserDetails activateUser(String token, HttpServletRequest request) {
        int id = generateToken.verifyToken(token);
        Optional<UserDetails> optional = userDetailsRepository.findById(id);

        if(optional.isPresent())
        {
        	UserDetails user=optional.get();
            user.setActivationStatus(true);
            userDetailsRepository.save(user);
            return user;
        }
        return null;
	}
    
    public UserDetails update(String token, UserDetails user, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		Optional<UserDetails> optional = userDetailsRepository.findById(id);
		if (optional.isPresent()) {
			UserDetails exstingUser=optional.get();
			exstingUser.setName(user.getName());
			exstingUser.setEmailId(user.getEmailId());
			exstingUser.setMobileNumber(user.getMobileNumber());
			exstingUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			userDetailsRepository.save(exstingUser);
			return exstingUser;
		}
		return null;
	}
    
    public UserDetails delete( String token,HttpServletRequest request) {
    	int id = generateToken.verifyToken(token);
    	Optional<UserDetails> optional = userDetailsRepository.findById(id);
    	if(optional.isPresent())
    	{
    		UserDetails deleteUser=optional.get();
    		userDetailsRepository.delete(deleteUser);
    		return deleteUser;
    	}
		return null;	
    }
    
    
    
}
