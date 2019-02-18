package com.bridgelabz.fundoonotes.util;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Component
public class GenerateTokenImpl  implements GenerateJWT{

	

	@Override
	public int verifyToken(String token) {
		Claims claims = Jwts.parser()        
                .setSigningKey(DatatypeConverter.parseBase64Binary("secretKey"))
                .parseClaimsJws(token).getBody();
             System.out.println("Id: " + claims.getId());
             return Integer.parseInt(claims.getId());
		
	}

}
