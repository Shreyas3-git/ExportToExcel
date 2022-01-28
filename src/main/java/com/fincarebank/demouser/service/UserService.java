package com.fincarebank.demouser.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fincarebank.demouser.customexception.UserException;
import com.fincarebank.demouser.dto.ForgotPasswordDTO;
import com.fincarebank.demouser.dto.LoginDTO;
import com.fincarebank.demouser.dto.Response;
import com.fincarebank.demouser.dto.ResponseDTO;
import com.fincarebank.demouser.dto.UserDetailsDTO;
import com.fincarebank.demouser.model.UserDetails;
import com.fincarebank.demouser.repository.UserRepository;
import com.fincarebank.demouser.tokenutils.UserToken;


@Service
public class UserService implements IUserService
{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserToken tokenUtils;
	
	@Override
	public Response verifyDetails(String token) 
	{
		long id = tokenUtils.decodeToken(token);
		Optional<UserDetails> user = userRepository.findById(id);
		if (user.isPresent()) 
		{
			user.get().setVerify(true);
			userRepository.save(user.get());
			return new Response("User verified successfully","200");
		} 
		return new Response("User verification failed","404");
	}


	@Override
	public List<UserDetails> retriveAllDetails() 
	{
		List<UserDetails> listOfUsers = userRepository.findAll();
		if(!listOfUsers.isEmpty())
		{
			return listOfUsers;
		}
		throw new UserException("No record found, UserNotFoundException caught",UserException.ExceptionType.USER_NOT_FOUND);
	}

	@Override
	public Response addUserDetails(UserDetailsDTO userdetails, MultipartFile imageFile)  
	{
		Optional<UserDetails> userss = userRepository.findByEmailId(userdetails.getEmailId());
		if (!userss.isPresent())
		{
			UserDetails user = new UserDetails();
			user.setFullName(userdetails.getFullName());
			user.setAddress(userdetails.getAddress());
			user.setMobile(userdetails.getMobile());
//			user.setProfilePicture(userdetails.getProfilePicture());
			user.setEmailId(userdetails.getEmailId());
			user.setPassword(userdetails.getPassword());
			user.setRegisteredDate(LocalDateTime.now());
			
			
//			String token = tokenUtils.createToken(user.getUserId());
			userRepository.save(user);
			
			return new Response("User added successfully","200");
		}
		throw new UserException("User is already exist",UserException.ExceptionType.USER_ALREADY_PRESENT);
	}

	@Override
	public Response userLoginCheck(LoginDTO login) 
	{
		Optional<UserDetails> user = userRepository.findByEmailIdAndPassword(login.getEmailId(), login.getPassword());
		if (user.isPresent()) 
		{
			String token = tokenUtils.createToken(user.get().getUserId());
			return new Response("Login successful ","200");
		}
		
		throw new UserException("Login failed incorrect emailId or password",UserException.ExceptionType.INCORRECT_DATA);
	}

	@Override
	public Response changeUserCredentails(ForgotPasswordDTO details) 
	{
		Optional<UserDetails> user = userRepository.findByEmailId(details.getEmailId());
		if (user.isPresent()) 
		{
			if (details.getPassword().equals(user.get().getPassword())  && details.getNewPassword().equals(details.getConfirmPassword())) 
			{
				UserDetails users = new UserDetails();
				users.setPassword(details.getNewPassword());
				userRepository.save(users);
				return new Response("User credentials change successfully","200");
			}
			throw new UserException("Incorrect password ",UserException.ExceptionType.INVALID_PASSWORD);
		}
		throw new UserException("Email not found",UserException.ExceptionType.EMAIL_NOT_FOUND);
	}


	@Override
	public Response deletebyId(long id) 
	{
		Optional<UserDetails> user = userRepository.findById(id);
		if(user.isPresent())
		{
			userRepository.deleteById(id);
			return new Response("Record deleted successfully","200");
		}
		return new Response("user not found","404");
	}


	@Override
	public Response addImage(MultipartFile imageFile,long id)  
	{
		Optional<UserDetails> user = userRepository.findById(id);
		if(user.isPresent())
		{
			String folder = "/Shreyas/";
			byte[] bytes;
			try 
			{
				bytes = imageFile.getBytes();
				Path path = Paths.get("folder" + imageFile.getOriginalFilename());
				Files.write(path,bytes);
				UserDetails users = new UserDetails();
				users.setProfilePicture(bytes);
				userRepository.save(users);
				return new Response("Image url saved successfully","200");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return new Response("Problem while adding image url","400");
	}

}
