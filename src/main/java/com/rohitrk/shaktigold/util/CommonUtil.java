package com.rohitrk.shaktigold.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rohitrk.shaktigold.model.ItemModel;

import javax.imageio.ImageIO;

@Component
public class CommonUtil {

	@Value("${app.work.dir}")
	private String appWorkDir;
	@Value("${app.ipaddress}")
	private String hostName;
	@Value("${app.port}")
	private String port;
	@Value("${app.protocol}")
	private String protocol;
	@Value("${server.contextPath}")
	private String serverContextPath;

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

	public String contructURL(String imageUrl) {
			return protocol + "://" + hostName + ":" + port + serverContextPath + "/" + imageUrl;
	}

	public  static String[] decryptAuthToken(String token) {
		return StringUtils.toEncodedString(Base64.getDecoder().decode(token.substring("Basic ".length())), Charset.defaultCharset()).split(":");
	}

	public boolean writeImageToDisk(String encodedImage, String absoluteFileName) {
		boolean imageWrite = false;

		String base64Image = encodedImage.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

			// write the image to a file
			File outputfile = new File(appWorkDir + absoluteFileName);
			File outputDir = new File(StringUtils.substringBeforeLast(outputfile.getAbsolutePath(), "\\"));
			if (!outputDir.exists()) {
				outputDir.mkdirs();
			}
			ImageIO.write(image, StringUtils.substringAfterLast(absoluteFileName, "."), outputfile);
			imageWrite = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageWrite;
	}
}
