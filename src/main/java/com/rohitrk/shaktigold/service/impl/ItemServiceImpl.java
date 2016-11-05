package com.rohitrk.shaktigold.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.ItemDAO;
import com.rohitrk.shaktigold.service.ItemService;

@Service("itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemDAO itemDAO;

}
