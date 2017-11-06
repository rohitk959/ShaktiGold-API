package com.rohitrk.shaktigold.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.model.OrderModel;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.UserAccountModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.validations.UserValidator;

@CrossOrigin
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

	@RequestMapping(value = "/registerCategory.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		boolean categoryAdded = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				categoryAdded = itemService.insertCategory(category);
			}
		}

		try {
			if (categoryAdded) {
				result.put("result", "SUCCESS");
				result.put("message", "Category Successfully added.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to add category.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping(value = "/getAllCategory.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getCategory(@RequestBody CategoryModel category, BindingResult bindingResult) {
		List<CategoryModel> categoryList = new ArrayList<CategoryModel>();

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				categoryList = itemService.getAllCategory();
			}
		}

		try {
			if (!categoryList.isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(categoryList);
				for (JsonNode node : rootNode) {
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
				result.put("result", "FAILURE");
				result.put("message", "Failed to get all categories.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping(value = "/registerSubCategory.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String registerSubCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		boolean subCategoryAdded = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				subCategoryAdded = itemService.insertSubCategory(category);
			}

			try {
				if (subCategoryAdded) {
					result.put("result", "SUCCESS");
					result.put("message", "Subcategory Successfully added.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to add category.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return json;
	}

	@RequestMapping(value = "/getAllSubCategory.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getSubCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		CategoryModel subCategoryList = null;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				subCategoryList = itemService.getAllSubCategory(category);
			}
		}

		try {
			if (null != subCategoryList && !subCategoryList.getSubcategory().isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(subCategoryList.getSubcategory());
				for (JsonNode node : rootNode) {
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
				result.put("result", "FAILURE");
				result.put("message", "Failed to get sub categories.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping(value = "/getItemTemplate.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getItemTemplate(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<SubCategoryProperty> itemTemplate = null;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemTemplate = itemService.getItemTemplate(item);
			}

			try {
				if (null != itemTemplate) {
					JsonNode rootNode = jsonMapper.valueToTree(itemTemplate);
					for (JsonNode node : rootNode) {
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
					result.put("result", "FAILURE");
					result.put("message", "Failed to get Item Template.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return json;
	}

	@RequestMapping(value = "/registerItem.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insertItem(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean itemAdded = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemAdded = itemService.registerItem(item);
			}

			try {
				if (itemAdded) {
					result.put("result", "SUCCESS");
					result.put("message", "Item Successfully added.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to add Item.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return json;
	}

	@RequestMapping(value = "/getAllItem.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getAllItems(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<ItemModel> itemList = new ArrayList<ItemModel>();
		boolean hasMoreItems = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemList = itemService.getAllItems(item);
				hasMoreItems = itemService.hasMoreItems(item);
			}

			try {
				if (null != itemList && !itemList.isEmpty()) {
					JsonNode rootNode = jsonMapper.valueToTree(itemList);
					for (JsonNode node : rootNode) {
						if (node instanceof ObjectNode) {
							ObjectNode object = (ObjectNode) node;
							object.remove("guid");
							object.remove("email");
							object.remove("sessionId");
							object.remove("categoryName");
							object.remove("subcategoryName");
							object.remove("recordActive");
							object.remove("orderStatus");
							object.remove("quantity");
							object.remove("limit");
							object.remove("offset");
							object.remove("orderDate");
							object.remove("orderCompleteDate");
							object.remove("invoiceNumber");

						}
					}
					
					Map<String, Object> subResult = new HashMap<String, Object>();
					subResult.put("hasMore", hasMoreItems);
					subResult.put("items", rootNode);

					result.put("result", "SUCCESS");
					result.put("message", subResult);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to fetch all Items.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	@RequestMapping(value = "/getItemDetails.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getItemDetails(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		ItemModel itemDetils = new ItemModel();

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemDetils = itemService.getItemDetails(item);
			}

			try {
				if (null != itemDetils) {
					JsonNode rootNode = jsonMapper.valueToTree(itemDetils);
						if (rootNode instanceof ObjectNode) {
							ObjectNode object = (ObjectNode) rootNode;
							object.remove("guid");
							object.remove("email");
							object.remove("sessionId");
							object.remove("categoryName");
							object.remove("subcategoryName");
							object.remove("quantity");
							object.remove("orderStatus");
							object.remove("recordActive");
							object.remove("limit");
							object.remove("offset");

					}

					result.put("result", "SUCCESS");
					result.put("message", rootNode);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to fetch Item details.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/putItemToCart.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String putItemToCart(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean itemAdded = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemAdded = itemService.putItemToCart(item);
			}

			try {
				if (itemAdded) {
					result.put("result", "SUCCESS");
					result.put("message", "Item has been successfully added to cart");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to add item in Cart.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/getItemsFromCart.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getItemsFromCart(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<ItemModel> itemsInCart = null;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemsInCart = itemService.getItemsFromCart(item);
			}

			try {
				if (null != itemsInCart && !itemsInCart.isEmpty()) {
					JsonNode rootNode = jsonMapper.valueToTree(itemsInCart);
					for (JsonNode node : rootNode) {
						if (node instanceof ObjectNode) {
							ObjectNode object = (ObjectNode) node;
							object.remove("guid");
							object.remove("email");
							object.remove("sessionId");
							object.remove("categoryName");
							object.remove("subcategoryName");
							object.remove("orderStatus");
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
					result.put("result", "FAILURE");
					result.put("message", "Failed to fetch Items from Cart.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/deleteItemFromCart.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String deleteItemFromCart(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean itemDeleted = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemDeleted = itemService.deleteItemFromCart(item);
			}

			try {
				if (itemDeleted) {
					result.put("result", "SUCCESS");
					result.put("message", "Item has been successfully deleted from cart");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to delete item from Cart.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/updateItemQtyInCart.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateItemQtyInCart(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean itemUpdated = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemUpdated = itemService.updateItemQtyInCart(item);
			}

			try {
				if (itemUpdated) {
					result.put("result", "SUCCESS");
					result.put("message", "Item has been successfully updated in cart");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to update item in Cart.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/placeOrder.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String placeOrder(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean orderPlaced = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				orderPlaced = itemService.placeOrder(item);
			}

			try {
				if (orderPlaced) {
					result.put("result", "SUCCESS");
					result.put("message", "Your Order has been placed successfully.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to place your order.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/updateOrder.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateOrder(@Valid @RequestBody OrderModel order, BindingResult bindingResult) {
		boolean orderUpdated = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(order.getEmail(), order.getSessionId())) {
				orderUpdated = itemService.updateOrder(order);
			}

			try {
				if (orderUpdated) {
					result.put("result", "SUCCESS");
					result.put("message", "Your Order has been updated successfully.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to updated your order.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/getAllUserOrder.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getAllUserOrder(@Valid @RequestBody ItemModel order, BindingResult bindingResult) {
		List<ItemModel> orders = null;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(order.getEmail(), order.getSessionId())) {
				orders = itemService.getAllUserOrder(order);
			}

			try {
				if (null != orders && !orders.isEmpty()) {
					JsonNode rootNode = jsonMapper.valueToTree(orders);
					for (JsonNode node : rootNode) {
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
					result.put("result", "FAILURE");
					result.put("message", "Failed to Fetch your orders.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/getEstimateSMS.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getEstimateSMS(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean orderUpdated = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				orderUpdated = itemService.sendEstimateSms(item);
			}

			try {
				if (orderUpdated) {
					result.put("result", "SUCCESS");
					result.put("message", "SMS sent successfully.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to send SMS.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/getAllAdminOrders.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getAllAdminOrders(@Valid @RequestBody ItemModel order, BindingResult bindingResult) {
		List<ItemModel> orders = null;

		try {
			orders = itemService.getAllAdminOrder(order);
			
			if (null != orders && !orders.isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(orders);
				for (JsonNode node : rootNode) {
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
				result.put("result", "FAILURE");
				result.put("message", "Failed to Fetch your orders.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@RequestMapping(value = "/updateOrderAdmin.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateOrderAdmin(@Valid @RequestBody ItemModel order, BindingResult bindingResult) {
		boolean orderUpdated = false;

		orderUpdated = itemService.updateOrderAdmin(order);

		try {
			if (orderUpdated) {
				result.put("result", "SUCCESS");
				result.put("message", "Your Order has been updated successfully.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to updated your order.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/getUserProfileByInvoiceNumber.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getUserProfileByInvoiceNumber(@Valid @RequestBody ItemModel order, BindingResult bindingResult) {
		UserAccountModel userProfile = null;

		userProfile = itemService.getUserProfileByInvoiceNumber(order);

		try {
			if (null != userProfile) {
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
				result.put("message", "Failed to get user profile.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/getAllSubCategoryAdmin.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getSubCategoryForAdmin(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {
		CategoryModel subCategoryList = null;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(category.getEmail(), category.getSessionId())) {
				subCategoryList = itemService.getAllSubCategoryForAdmin(category);
			}
		}

		try {
			if (null != subCategoryList && !subCategoryList.getSubcategory().isEmpty()) {
				JsonNode rootNode = jsonMapper.valueToTree(subCategoryList.getSubcategory());
				for (JsonNode node : rootNode) {
					if (node instanceof ObjectNode) {
						ObjectNode object = (ObjectNode) node;
						object.remove("guid");
						object.remove("properties");
					}
				}

				result.put("result", "SUCCESS");
				result.put("message", rootNode);

				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to get sub categories.");
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/enableDisableSubcategory.htm", method = RequestMethod.GET)
	public String enableDisableSubcategory(@RequestBody @RequestParam String subcategory, @RequestParam boolean hidden ) {
		boolean hiddenResult = false;

		hiddenResult = itemService.enableDisableSubcategory(subcategory, hidden);

		try {
			if (hiddenResult) {
				result.put("result", "SUCCESS");
				if(hidden){
					result.put("message", subcategory.concat(" is disabled."));
				} else {
					result.put("message", subcategory.concat(" is enabled."));
				}

				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to Enable / Disable ".concat(subcategory));
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/getAllItemAdmin.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getAllItemsAdmin(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		List<ItemModel> itemList = new ArrayList<ItemModel>();

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				itemList = itemService.getAllItemsAdmin(item);
			}

			try {
				if (null != itemList && !itemList.isEmpty()) {
					JsonNode rootNode = jsonMapper.valueToTree(itemList);
					for (JsonNode node : rootNode) {
						if (node instanceof ObjectNode) {
							ObjectNode object = (ObjectNode) node;
							object.remove("guid");
							object.remove("email");
							object.remove("sessionId");
							object.remove("categoryName");
							object.remove("subcategoryName");
							object.remove("orderStatus");
							object.remove("quantity");
							object.remove("limit");
							object.remove("offset");
							object.remove("orderDate");
							object.remove("orderCompleteDate");
							object.remove("invoiceNumber");

						}
					}
					
					result.put("result", "SUCCESS");
					result.put("message", rootNode);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to fetch all Items.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	
	@RequestMapping(value = "/enableDisableItem.htm", method = RequestMethod.GET)
	public String enableDisableItem(@RequestBody @RequestParam String itemId, @RequestParam boolean hidden ) {
		boolean hiddenResult = false;

		hiddenResult = itemService.enableDisableItem(itemId, hidden);

		try {
			if (hiddenResult) {
				result.put("result", "SUCCESS");
				if(hidden){
					result.put("message", "Item ID : ".concat(itemId).concat(" is disabled."));
				} else {
					result.put("message", "Item ID : ".concat(itemId).concat(" is enabled."));
				}

				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to Enable / Disable ".concat("Item ID : ".concat(itemId)));
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/deleteSubcategory.htm", method = RequestMethod.GET)
	public String deleteSubcategory(@RequestBody @RequestParam String subcategory ) {
		boolean hiddenResult = false;

		hiddenResult = itemService.deleteSubcategory(subcategory);

		try {
			if (hiddenResult) {
				result.put("result", "SUCCESS");
				result.put("message", subcategory.concat(" is deleted."));

				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed delete ".concat(subcategory));
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/deleteItem.htm", method = RequestMethod.GET)
	public String deleteItem(@RequestBody @RequestParam String itemId) {
		boolean hiddenResult = false;

		hiddenResult = itemService.deleteItem(itemId);

		try {
			if (hiddenResult) {
				result.put("result", "SUCCESS");
				result.put("message", "Item ID : ".concat(itemId).concat(" is deleted."));

				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			} else {
				result.put("result", "FAILURE");
				result.put("message", "Failed to delete ".concat("Item ID : ".concat(itemId)));
				json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	@RequestMapping(value = "/getEstimateDB.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getEstimateDB(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
		boolean orderUpdated = false;

		if (!bindingResult.hasErrors()) {
			if (userService.validateUserSession(item.getEmail(), item.getSessionId())) {
				orderUpdated = itemService.sendEstimateDB(item);
			}

			try {
				if (orderUpdated) {
					result.put("result", "SUCCESS");
					result.put("message", "You will receive a notification with approx estimation shortly.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to send Notification to ShaktiGold.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
}
