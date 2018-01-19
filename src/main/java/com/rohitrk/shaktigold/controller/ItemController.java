package com.rohitrk.shaktigold.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitrk.shaktigold.model.ItemModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController("itemController")
public class ItemController {

    @Autowired
    ItemService itemService;

    private String json;
    Map<String, Object> result = new HashMap<String, Object>();
    ObjectMapper jsonMapper = new ObjectMapper();

    @RequestMapping(value = "/admin/{category}/{subcategory}/item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addItem(@PathVariable("category") String category, @PathVariable("subcategory") String subcategory,
                                     @Valid @RequestBody ItemModel item, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            itemService.addItem(category, subcategory, item);
        }
        return CustomResponseEntity.ok("Item added successfully.");
    }

    @RequestMapping(value = "/user/{category}/{subcategory}/items", method = RequestMethod.GET)
    public ResponseEntity<?> getAllItemsUser(@PathVariable(required = false) String category, @PathVariable String subcategory, @RequestParam int limit, @RequestParam int offset) {
        List<ItemModel> itemList = itemService.getAllItemsUser(category, subcategory, limit, offset);
        boolean hasMoreItems = itemService.hasMoreItems(category, subcategory, limit, offset);

        Map<String, Object> result = new HashMap<>();
        result.put("hasMoreItems", hasMoreItems);
        result.put("items", itemList);

        return CustomResponseEntity.ok(result);
    }

    @RequestMapping(value = "/user/itemDetails/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getItemDetails(@PathVariable int id) {
        return CustomResponseEntity.ok(itemService.getItemDetails(id));
    }

    @RequestMapping(value = "/admin/{category}/{subcategory}/items", method = RequestMethod.GET)
    public ResponseEntity<?> getAllItemsAdmin(@PathVariable(required = false) String category, @PathVariable String subcategory) {
        return CustomResponseEntity.ok(itemService.getAllItemsAdmin(category, subcategory));
    }

    @RequestMapping(value = "/admin/item/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
        itemService.deleteItem(itemId);
        return CustomResponseEntity.ok("Item ID : ".concat(itemId).concat(" is deleted."));
    }

    @RequestMapping(value = "/admin/item/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> enableDisableItem(@PathVariable String itemId, @RequestParam boolean enabled) {
        itemService.enableDisableItem(itemId, enabled);
        if (enabled)
            return CustomResponseEntity.ok("Item ID : ".concat(itemId).concat(" is enabled."));
        else
            return CustomResponseEntity.ok("Item ID : ".concat(itemId).concat(" is disabled."));
    }

    @RequestMapping(value = "/getEstimateSMS.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getEstimateSMS(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
        boolean orderUpdated = false;

        if (!bindingResult.hasErrors()) {
//			if (userService.validateUserSessionByEmail(item.getEmail(), item.getSessionId())) {
            orderUpdated = itemService.sendEstimateSms(item);
//			}

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

    @RequestMapping(value = "/getEstimateDB.htm", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getEstimateDB(@Valid @RequestBody ItemModel item, BindingResult bindingResult) {
        boolean orderUpdated = false;

        if (!bindingResult.hasErrors()) {
//			if (userService.validateUserSessionByEmail(item.getEmail(), item.getSessionId())) {
            orderUpdated = itemService.sendEstimateDB(item);
//			}

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
