package com.fincarebank.demouser.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fincarebank.demouser.dto.ForgotPasswordDTO;
import com.fincarebank.demouser.dto.LoginDTO;
import com.fincarebank.demouser.dto.Response;
import com.fincarebank.demouser.dto.UserDetailsDTO;
import com.fincarebank.demouser.export.UserExporter;
import com.fincarebank.demouser.model.UserDetails;
import com.fincarebank.demouser.service.IUserService;

@RestController
@RequestMapping(path="/demouser")
public class UserController 
{
	@Autowired
	private IUserService userService;
	
	@GetMapping("/verifyuser")
	public Response verifyUser(@RequestParam String token)
	{
		Response response = userService.verifyDetails(token);
		return response;
	}

	
	@GetMapping(path = "/getallusers")
	public List<UserDetails> getAllusers() throws FileNotFoundException, IOException
	{
		List<UserDetails> listOfUsers = userService.retriveAllDetails();
		return listOfUsers;
	}
	
	@GetMapping(path = "/export")
	public void exportToExcel(HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		String headerKey = "content-Disposition";
		String headerValue = "attachment; filename=User_Info.xslx";
		response.setHeader(headerKey, headerValue);
		
		List<UserDetails> listOfUsers = userService.retriveAllDetails();
		UserExporter exporter = new UserExporter(listOfUsers);
		exporter.export(response);
	}
	
	@PostMapping(path = "/adduser")
	public Response registerUser(@RequestBody UserDetailsDTO user,@RequestParam MultipartFile imageFile) 
	{
		Response response = userService.addUserDetails(user,imageFile);
		return response;
	}
	
	@PutMapping(path = "/addimage")
	public Response addUserProfile(@RequestParam long id,@RequestParam MultipartFile imageFile) throws IOException
	{
		Response response = userService.addImage(imageFile,id);
		return response;
	}
	
	@PostMapping(path="/userlogin")
	public Response userLogin(@RequestBody LoginDTO login)
	{
		Response response =  userService.userLoginCheck(login);
		return response;
	}
	
	@PutMapping(path = "/forgotpassword")
	public Response modifyUserCredentials(@RequestBody ForgotPasswordDTO user)
	{
		Response response = userService.changeUserCredentails(user);
		return response;
	}
	
	@DeleteMapping(path = "/deleteuser/{id}")
	public Response deleteUser(@PathVariable long id)
	{
		Response response = userService.deletebyId(id);
		return response;
	}
}
