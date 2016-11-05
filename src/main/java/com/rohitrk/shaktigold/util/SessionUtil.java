package com.rohitrk.shaktigold.util;

import java.util.UUID;

public class SessionUtil {
	
	public static String getSessionID() {
		return UUID.randomUUID().toString();
	}
}
