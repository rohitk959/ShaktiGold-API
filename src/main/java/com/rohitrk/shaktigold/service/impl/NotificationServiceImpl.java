package com.rohitrk.shaktigold.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohitrk.shaktigold.dao.NotificationDAO;
import com.rohitrk.shaktigold.model.NotificationModel;
import com.rohitrk.shaktigold.service.ItemService;
import com.rohitrk.shaktigold.service.NotificationService;
import com.rohitrk.shaktigold.util.Constants;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{
	@Autowired
	NotificationDAO notifyDAO;
	@Autowired
	ItemService itemService;
	
	@Override
	public int getNotificationCount(String email) {
		if(email.equalsIgnoreCase(Constants.ADMIN_EMAIL))
			return notifyDAO.getNotificationCountAdmin(email);
		else
			return notifyDAO.getNotificationCount(email);
	}

	@Override
	public List<NotificationModel> getNotificationList(String email) {
		List<NotificationModel> notify = null;
		if(email.equalsIgnoreCase(Constants.ADMIN_EMAIL))
			notify = notifyDAO.fetchNotificationListAdmin();
		else {
			notify = notifyDAO.fetchNotificationList(email);
		}
		
		if(!notify.isEmpty()) {
			for(NotificationModel notification : notify) {
				notification.setItem(itemService.getItemDetails(1));
			}
		}
		
		return notify;
	}
}
