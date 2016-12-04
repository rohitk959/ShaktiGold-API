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
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
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
	
	@RequestMapping(value="/getAllSubCategory.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getSubCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		CategoryModel subCategoryList = null;
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				subCategoryList = itemService.getAllSubCategory(category);
			}
		}
		
		try{
			if(null != subCategoryList && !subCategoryList.getSubcategory().isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(subCategoryList.getSubcategory());
				for(JsonNode node : rootNode ) {
					if (node instanceof ObjectNode) {
				        ObjectNode object = (ObjectNode) node;
				        object.remove("guid");
				        object.remove("recordActive");
				        object.remove("properties");
				    }
				}
				
				result.put("result", "SUCCESS");
				result.put("message", rootNode);
				
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE.");
				result.put("message", "Failed to get sub categories.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value="/getItemTemplate.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getItemTemplate(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<SubCategoryProperty> itemTemplate = null;
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemTemplate = itemService.getItemTemplate(item);
			}
			
			try{
				if(null != itemTemplate) {
					JsonNode rootNode = jsonMapper.valueToTree(itemTemplate);
					for(JsonNode node : rootNode ) {
						if (node instanceof ObjectNode) {
					        ObjectNode object = (ObjectNode) node;
					        object.remove("guid");
					        object.remove("subcategory_fk");
					    }
					}
					
					result.put("result", "SUCCESS");
					result.put("message", rootNode);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE.");
					result.put("message", "Failed to get Item Template.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	@RequestMapping(value="/registerItem.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String insertItem(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean itemAdded = false;
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemAdded = itemService.registerItem(item);
			}
			
			try{
				if(itemAdded) {
					result.put("result", "SUCCESS");
					result.put("message", "Item Successfully added.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE.");
					result.put("message", "Failed to add Item.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	@RequestMapping(value="/getAllItem.htm", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getAllItems(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<ItemModel> itemList = new ArrayList<ItemModel>();
		
		if(!bindingResult.hasErrors()) {
			if(userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemList = itemService.getAllItems(item);
			}
			
			try{
				if(null != itemList && !itemList.isEmpty()) {
					JsonNode rootNode = jsonMapper.valueToTree(itemList);
					for(JsonNode node : rootNode ) {
						if (node instanceof ObjectNode) {
					        ObjectNode object = (ObjectNode) node;
					        object.remove("guid");
					        object.remove("email");
					        object.remove("sessionId");
					        object.remove("categoryName");
					        object.remove("subcategoryName");
					        object.remove("recordActive");
					        object.remove("itemProperty");
					        object.remove("limit");
					        object.remove("offset");
					        
					    }
					}
					
					result.put("result", "SUCCESS");
					result.put("message", rootNode);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE.");
					result.put("message", "Failed to fetch all Items.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
}
