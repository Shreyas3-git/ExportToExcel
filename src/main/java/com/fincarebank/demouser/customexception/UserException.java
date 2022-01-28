package com.fincarebank.demouser.customexception;

public class UserException extends RuntimeException
{
	
	public enum ExceptionType
	{
		USER_ALREADY_PRESENT,
		EMAIL_NOT_FOUND,
		USER_NOT_FOUND,
		INVALID_PASSWORD,
		INCORRECT_DATA
	}
	
	private UserException.ExceptionType type;
	
	public UserException(String message,UserException.ExceptionType type)
	{
		super(message);
		this.type = type;
	}
}
