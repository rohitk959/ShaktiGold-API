package com.rohitrk.shaktigold.service;

import java.util.List;

import com.rohitrk.shaktigold.model.NotificationModel;

public interface NotificationService {

	int getNotificationCount(String email);

	List<NotificationModel> getNotificationList(String email);

}
