package com.fincarebank.demouser.tokenutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

@Component
public class UserToken 
{
	private static final String TOKEN_SECRET = "Shreyas";
	private static final Logger LOGGER = LoggerFactory.getLogger(UserToken.class);

	public String createToken(long id)
	{
		try {
			LOGGER.info("user_Id"+id);
			LOGGER.info("Token creation:");
			
			Algorithm algorithm = Algorithm.HMAC384(TOKEN_SECRET);
			
			String token = JWT.create()
			.withClaim(TOKEN_SECRET, id)
			.sign(algorithm);
			
			return token;
		}
		catch(JWTCreationException exception)
		{
			exception.printStackTrace();
		}
		
		catch(IllegalArgumentException exception)
		{
			exception.printStackTrace();
		}
		return null;
	}
	
	public long decodeToken(String token)
	{
		long userId;
		//for verification algorithm;
		Verification verification = null;
		
		
		try 
		{
			verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			
		} 
		catch (IllegalArgumentException exception) 
		{
			exception.printStackTrace();
		}
		
		JWTVerifier jwtVerifier = verification.build();
			//decode token here
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		
		
		Claim claim = decodedJWT.getClaim("user_id");
		userId = claim.asLong();
		return userId;
	}

	
}
