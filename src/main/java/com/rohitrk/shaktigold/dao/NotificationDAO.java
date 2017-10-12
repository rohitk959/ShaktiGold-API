package com.rohitrk.shaktigold.dao;

import java.util.List;

import com.rohitrk.shaktigold.model.NotificationModel;

public interface NotificationDAO {

	int getNotificationCountAdmin(String email);

	int getNotificationCount(String email);

	List<NotificationModel> fetchNotificationList(String email);

	List<NotificationModel> fetchNotificationListAdmin();

}
