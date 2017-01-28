package com.rohitrk.shaktigold.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rohitrk.shaktigold.model.ItemModel;

@Component
public class CommonUtil {
	
	public static String getSmsBody(String customerName, String customerMobile, String itemId) {
		StringBuilder smsBody = new StringBuilder();
		smsBody.append(Constants.smsHeader);
		smsBody.append(Constants.BLANK);
		smsBody.append(Constants.NAME + customerName);
		smsBody.append(Constants.BLANK);
		smsBody.append(Constants.MOBILE + customerMobile);
		smsBody.append(Constants.BLANK);
		smsBody.append(Constants.ITEM_ID + itemId);
		
		return smsBody.toString();
	}

	public static void prepareImageURL(List<ItemModel> items, String protocol, String hostName, String port) {
		for (ItemModel lItem : items) {
			lItem.setImgUrl(protocol + "://" + hostName + ":" + port + "/ShaktiGold" + lItem.getImgUrl());
		}
	}
}
