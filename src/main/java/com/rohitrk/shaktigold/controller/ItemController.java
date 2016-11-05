package com.rohitrk.shaktigold.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.UserService;

@RestController
public class ItemController {
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;
}
