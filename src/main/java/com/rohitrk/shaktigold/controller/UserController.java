package com.rohitrk.shaktigold.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.validations.ApplicationValidator;
import com.rohitrk.shaktigold.validations.UserValidator;

@RestController("employeeController")
public class UserController {
	@Autowired
	UserService userService;
	String json;
	Map<String, Object> result = new HashMap<String, Object>();
	UserValidator validator = new UserValidator();
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@RequestMapping(value="/login.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String enableLogin(@RequestBody UserAccountModel userAccount) {
		String message = "Invalid Username or Password.";
		String sessionId = null;
		
		if(ApplicationValidator.validateLoginFields(userAccount)) {
			sessionId = userService.enableLogin(userAccount);
		}
		
		try {
			if(StringUtils.isNotEmpty(sessionId)) {
				result.put("result", "SUCCESS");
				result.put("message", "Login Successfull.");
				result.put("sessionID", sessionId);
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", message);
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/registerUser.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String registerUser(@Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
		boolean userRegistration = false;
		String message = null;
		
		if(!bindingResult.hasErrors()) {
			if (!userService.userExists(userAccount)) {
				userRegistration = userService.registerUser(userAccount);
			} else {
				message = "User Already Exists.";
			}
		} else {
			
		}
		
		try {
			if(userRegistration) {
				result.put("result", "SUCCESS");
				result.put("message", "User Registration Successful.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", message == null ? "User Registration Failed. Contact System Administrator." : message);
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/updateProfile.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String updateProfile(@Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
		boolean userProfileProfile = false;
		
		try {
			if(!bindingResult.hasErrors()) {
			
				if(userService.validateUserSession(userAccount.getEmail(), userAccount.getSessionId())) {
					userProfileProfile = userService.updateProfile(userAccount);
					if(userProfileProfile) {
						result.put("result", "SUCCESS");
						result.put("message", "Profile Updated Successfully.");
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					} else {
						result.put("result", "FAILURE");
						result.put("message", "Unable to update profile. Please contact System Administrator.");
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					}
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Invalid Session.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Validation Failed.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/getUserProfile.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getUserProfile(@Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
		UserAccountModel userProfile = null;
		
		try {
			if(!bindingResult.hasErrors()) {
				if(userService.validateUserSession(userAccount.getEmail(), userAccount.getSessionId())) {
					userProfile = userService.getUserDetails(userAccount.getEmail());
					if(null != userProfile) {
						JsonNode rootNode = jsonMapper.valueToTree(userProfile);
						ObjectNode mainObject = (ObjectNode) rootNode;
						mainObject.remove("guid");
						mainObject.remove("role");
						mainObject.remove("password");
						mainObject.remove("newPassword");
						mainObject.remove("passwordHash");
						mainObject.remove("passwordSalt");
						mainObject.remove("createdDate");
						mainObject.remove("sessionId");
						for (JsonNode node : rootNode) {
							if (node instanceof ObjectNode) {
								ObjectNode object = (ObjectNode) node;
								object.remove("guid");
								object.remove("updatedDate");
							}
						}
						
						result.put("result", "SUCCESS");
						result.put("message", rootNode);
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					} else {
						result.put("result", "FAILURE");
						result.put("message", "Unable to get user profile. Please contact System Administrator.");
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					}
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Invalid Session.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Validation Failed.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/changePassword.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String changePassword(@Valid @RequestBody UserAccountModel userAccount, BindingResult bindingResult) {
		boolean passwordChanged = false;
		
		try {
			if(!bindingResult.hasErrors()) {
			
				if(userService.validateUserSession(userAccount.getEmail(), userAccount.getSessionId())) {
					passwordChanged = userService.changePassword(userAccount);
					if(passwordChanged) {
						result.put("result", "SUCCESS");
						result.put("message", "Password Updated Successfully.");
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					} else {
						result.put("result", "FAILURE");
						result.put("message", "Unable to update password. Please contact System Administrator.");
						json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
					}
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Invalid Session.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Validation Failed.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}