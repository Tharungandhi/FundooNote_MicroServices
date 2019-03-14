package com.bridgelabz.fundoonotes.util;

public interface GenerateJWT {

	 String generateToken(String id);
	   
	    int verifyToken(String token);
}
