package com.fincarebank.demouser.dto;

public class Response 
{

	private String message;
	private String statusCode;
	

	public Response(String message, String statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	
	public Response()
	{
		super();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
