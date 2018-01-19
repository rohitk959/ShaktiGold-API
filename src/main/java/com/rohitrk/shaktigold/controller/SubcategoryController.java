package com.rohitrk.shaktigold.controller;

import com.rohitrk.shaktigold.model.SubcategoryModel;
import com.rohitrk.shaktigold.service.SubcategoryService;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("subcategoryController")
public class SubcategoryController {

    @Autowired
    SubcategoryService subcategoryService;

    @RequestMapping(value = "/admin/{category}/subcategory", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerSubCategory(@PathVariable String category, @Valid @RequestBody SubcategoryModel subcategory, BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            subcategoryService.addSubcategory(category, subcategory);
        }
        return CustomResponseEntity.ok("Subcategory added successfully.");
    }

    @RequestMapping(value = "/user/{category}/subcategory", method = RequestMethod.GET)
    public ResponseEntity<?> getSubCategoryUser(@PathVariable String category) {
        return CustomResponseEntity.ok(subcategoryService.getAllSubcategoryUser(category));
    }

    @RequestMapping(value = "/admin/{category}/{subcategory}/itemTemplate", method = RequestMethod.GET)
    public ResponseEntity<?> getItemTemplate(@PathVariable String category, @PathVariable String subcategory) {
        return CustomResponseEntity.ok(subcategoryService.getItemTemplate(category, subcategory));
    }

    @RequestMapping(value = "/admin/{category}/subcategory", method = RequestMethod.GET)
    public ResponseEntity<?> getSubCategoryAdmin(@PathVariable String category) {
        return CustomResponseEntity.ok(subcategoryService.getAllSubcategoryAdmin(category));
    }

    @RequestMapping(value = "/admin/{category}/{subcategory}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubcategory(@PathVariable String category, @PathVariable String subcategory) {
        subcategoryService.deleteSubcategory(category, subcategory);
        return CustomResponseEntity.ok("Subcategory deleted successfully.");
    }

    @RequestMapping(value = "/admin/{category}/{subcategory}", method = RequestMethod.PATCH)
    public ResponseEntity<?> enableDisableSubcategory(@PathVariable String category, @PathVariable String subcategory, @RequestParam boolean enabled) {
        subcategoryService.enableDisableSubcategory(subcategory, enabled);

        if (enabled) {
            return CustomResponseEntity.ok(subcategory.concat(" is enabled."));
        } else {
            return CustomResponseEntity.ok(subcategory.concat(" is disabled."));
        }

    }
}
