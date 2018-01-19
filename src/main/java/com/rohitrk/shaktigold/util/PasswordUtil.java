package com.rohitrk.shaktigold.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PasswordUtil {
	// The higher the number of iterations the more
	// expensive computing the hash is for us and
	// also for an attacker.
	private static final int iterations = 20 * 1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;

	/**
	 * Computes a salted PBKDF2 hash of given plaintext password suitable for
	 * storing in a database. Empty passwords are not supported.
	 */
	public static String getSalt() throws Exception {
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		return Base64.encodeBase64String(salt);
	}

	/**
	 * Checks whether given plaintext password corresponds to a stored salted
	 * hash of the password.
	 */
	public static boolean check(String password, String storedHash, String storedSalt) throws Exception {
		if (StringUtils.isEmpty(storedHash) || StringUtils.isEmpty(storedSalt)) {
			throw new IllegalStateException("Stored Password hash or Password Salt is missing");
		}
		String hashOfInput = hash(password, storedSalt);
		if(hashOfInput.equals(storedHash)) {
			return true;
		} else {
			log.warn("Invalid Username and Password.");
			return false;
		}
	}

	public static String hash(String password, String salt) throws Exception {
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException("Empty passwords are not supported.");
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), Base64.decodeBase64(salt), iterations, desiredKeyLen));
		return Base64.encodeBase64String(key.getEncoded());
	}
}
