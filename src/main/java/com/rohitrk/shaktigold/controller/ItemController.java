package com.rohitrk.shaktigold.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.validations.UserValidator;

@RestController
public class ItemController {
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;

	private String json;
	Map<String, Object> result = new HashMap<String, Object>();
	UserValidator validator = new UserValidator();
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@RequestMapping(value="/registerCategory.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String registerCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		boolean categoryAdded = false;
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				categoryAdded = itemService.insertCategory(category);
			}
		}
			
		try{
			if(categoryAdded) {
				result.put("result", "SUCCESS");
				result.put("message", "Category Successfully added.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE.");
				result.put("message", "Failed to add category.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}

	@RequestMapping(value="/getAllCategory.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getCategory(@RequestBody CategoryModel category, BindingResult bindingResult) {
		List<CategoryModel> categoryList = new ArrayList<CategoryModel>();
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				categoryList = itemService.getAllCategory();
			}
		}
		
		try{
			if(!categoryList.isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(categoryList);
				for(JsonNode node : rootNode ) {
					if (node instanceof ObjectNode) {
				        ObjectNode object = (ObjectNode) node;
				        object.remove("guid");
				        object.remove("recordActive");
				        object.remove("sessionId");
				        object.remove("email");
				        object.remove("subcategory");
				    }
				}
				
				result.put("result", "SUCCESS");
				result.put("message", rootNode);
				
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE.");
				result.put("message", "Failed to get all categories.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/registerSubCategory.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String registerSubCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		boolean subCategoryAdded = false;
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				subCategoryAdded = itemService.insertSubCategory(category);
			}
			
			try{
				if(subCategoryAdded) {
					result.put("result", "SUCCESS");
					result.put("message", "Subcategory Successfully added.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE.");
					result.put("message", "Failed to add category.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
}
