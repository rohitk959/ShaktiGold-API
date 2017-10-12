package com.rohitrk.shaktigold.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rohitrk.shaktigold.dao.NotificationDAO;
import com.rohitrk.shaktigold.mapper.NotificationMapper;
import com.rohitrk.shaktigold.model.NotificationModel;
import com.rohitrk.shaktigold.query.NotificationQuery;

@Repository("notificationDao")
public class NotificationDAOImpl implements NotificationDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public int getNotificationCount(String email) {
		int notificationCount = 0;
		
		try {
			notificationCount = jdbcTemplate.queryForObject(NotificationQuery.GET_USER_NOTIFICATION_COUNT, Integer.class, new Object[] { email } );
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return notificationCount;
	}

	@Override
	public int getNotificationCountAdmin(String email) {
		int notificationCount = 0;
		
		try {
			notificationCount = jdbcTemplate.queryForObject(NotificationQuery.GET_ADMIN_NOTIFICATION_COUNT, Integer.class );
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return notificationCount;
	}

	@Override
	public List<NotificationModel> fetchNotificationList(String email) {
		List<NotificationModel> notify = null;
		
		try {
			notify = jdbcTemplate.query(NotificationQuery.GET_USER_NOTIFICATION_LIST,new NotificationMapper(), new Object[] { email });
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return notify;
	}

	@Override
	public List<NotificationModel> fetchNotificationListAdmin() {
		List<NotificationModel> notify = null;
		
		try {
			notify = jdbcTemplate.query(NotificationQuery.GET_ADMIN_NOTIFICATION_LIST,new NotificationMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return notify;
	}
}