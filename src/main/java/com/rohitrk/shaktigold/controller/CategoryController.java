package com.rohitrk.shaktigold.controller;

import com.rohitrk.shaktigold.model.CategoryModel;
import com.rohitrk.shaktigold.service.CategoryService;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("categoryController")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/admin/category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCategory(@Valid @RequestBody CategoryModel category, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            categoryService.addCategory(category);
        }

        return CustomResponseEntity.ok("Category added successfully.");
    }

    @RequestMapping(value = "/user/category", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCategory() {
        return CustomResponseEntity.ok(categoryService.getAllCategory());
    }
}
