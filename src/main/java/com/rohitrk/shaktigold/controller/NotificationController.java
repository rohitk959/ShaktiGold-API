package com.rohitrk.shaktigold.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitrk.shaktigold.model.NotificationModel;
import com.rohitrk.shaktigold.service.NotificationService;
import com.rohitrk.shaktigold.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
public class NotificationController {
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notifyService;

    private String json;
    Map<String, Object> result = new HashMap<String, Object>();
    ObjectMapper jsonMapper = new ObjectMapper();

    @RequestMapping(value = "/getNotificationCount.htm", method = RequestMethod.GET)
    public String getNotificationCount(@RequestBody @RequestParam String email, @RequestParam String sessionId) {
        int count = -1;

//		if (userService.validateUserSessionByEmail(email, sessionId)) {
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
//		}

        return json;
    }

    @RequestMapping(value = "/getNotificationList.htm", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getNotificationList(@RequestBody @RequestParam String email, @RequestParam String sessionId) {
        List<NotificationModel> notify = null;
//		if (userService.validateUserSessionByEmail(email, sessionId)) {
        notify = notifyService.getNotificationList(email);

        try {
            if (Objects.nonNull(notify)) {
                result.put("result", "SUCCESS");
                Map<String, List<NotificationModel>> countResult = new HashMap<>();
                countResult.put("notificationList", notify);
                result.put("message", countResult);
                json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            } else {
                result.put("result", "FAILURE");
                result.put("message", "Failed to get the notification List.");
                json = jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//		}

        return json;
    }
}
