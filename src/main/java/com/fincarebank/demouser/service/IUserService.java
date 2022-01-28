package com.fincarebank.demouser.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.fincarebank.demouser.dto.ForgotPasswordDTO;
import com.fincarebank.demouser.dto.LoginDTO;
import com.fincarebank.demouser.dto.Response;
import com.fincarebank.demouser.dto.ResponseDTO;
import com.fincarebank.demouser.dto.UserDetailsDTO;
import com.fincarebank.demouser.model.UserDetails;

public interface IUserService 
{
	public Response verifyDetails(String token);
	public List<UserDetails> retriveAllDetails();
	public Response addUserDetails(UserDetailsDTO user,MultipartFile imageFile);
	public Response userLoginCheck(LoginDTO login); 
	public Response changeUserCredentails(ForgotPasswordDTO details);
	public Response deletebyId(long id);
	public Response addImage(MultipartFile imageFile,long id) throws IOException;
}
