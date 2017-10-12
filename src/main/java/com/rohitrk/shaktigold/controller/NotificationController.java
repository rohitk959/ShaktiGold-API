package com.rohitrk.shaktigold.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitrk.shaktigold.model.NotificationModel;
import com.rohitrk.shaktigold.service.NotificationService;
import com.rohitrk.shaktigold.service.UserService;
import com.rohitrk.shaktigold.validations.UserValidator;

@RestController
public class NotificationController {
	@Autowired
	UserService userService;
	
	@Autowired
	NotificationService notifyService;
	
	private String json;
	Map<String, Object> result = new HashMap<String, Object>();
	UserValidator validator = new UserValidator();
	ObjectMapper jsonMapper = new ObjectMapper();

	@RequestMapping(value = "/getNotificationCount.htm", method = RequestMethod.GET)
	public String getNotificationCount(@RequestBody @RequestParam String email, @RequestParam String sessionId) {
		int count = -1;
		
		if (userService.validateUserSession(email, sessionId)) {
			count = notifyService.getNotificationCount(email);

			try {
				if (-1 != count) {
					result.put("result", "SUCCESS");
					Map<String, Integer> countResult = new HashMap<String, Integer>();
					countResult.put("notificationCount", count);
					result.put("message", countResult);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to get the notification count.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
	
	@RequestMapping(value = "/getNotificationList.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String getNotificationList(@RequestBody @RequestParam String email, @RequestParam String sessionId) {
		List<NotificationModel> notify = null;
		if (userService.validateUserSession(email, sessionId)) {
			notify = notifyService.getNotificationList(email);
			
			try {
				if (-1 != count) {
					result.put("result", "SUCCESS");
					Map<String, Integer> countResult = new HashMap<String, Integer>();
					countResult.put("notificationCount", count);
					result.put("message", countResult);
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				} else {
					result.put("result", "FAILURE");
					result.put("message", "Failed to get the notification count.");
					json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
		return json;
	}
}
