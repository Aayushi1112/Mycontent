package com.integration.auth.service.auth.encoder;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.service.auth.exceptions.HashingPasswordException;
import com.integration.auth.service.auth.exceptions.PasswordEncodingException;
import com.integration.auth.service.auth.exceptions.PasswordMismatchException;
import com.integration.auth.service.auth.exceptions.SaltKeyException;
import com.integration.auth.common.util.TargetTypes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class CustomPasswordEncoder implements PasswordEncoder {

	private static final int ITERATIONS = 100;

	private static final String ALGORITHM = "SHA-256";

	private final byte[] salt;

	public CustomPasswordEncoder(String saltKey) {
		this.salt = generateSalt(saltKey);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			byte[] passwordBytes = rawPassword.toString().getBytes(StandardCharsets.UTF_8);
			byte[] hashedBytes = hashPassword(passwordBytes, salt, ITERATIONS);
			return bytesToHex(hashedBytes);
		}
		catch (Exception ex) {
			throw new PasswordEncodingException(ex,
					new Errors(TargetTypes.PASSWORD_ENCODING_ERROR.name(),
							ErrorCodesAndMessages.PASSWORD_ENCODING_ERROR_MESSAGE,
							ErrorCodesAndMessages.PASSWORD_ENCODING_ERROR_CODE));
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		byte[] passwordBytes = rawPassword.toString().getBytes(StandardCharsets.UTF_8);
		byte[] hashedBytes = hexToBytes(encodedPassword);
		byte[] testHashedBytes = hashPassword(passwordBytes, salt, ITERATIONS);
		if (MessageDigest.isEqual(hashedBytes, testHashedBytes)) {
			return true;
		}
		else {
			throw new PasswordMismatchException(new Errors(TargetTypes.PASSWORD_MATCHING_ERROR.name(),
					ErrorCodesAndMessages.PASSWORD_MISMATCH_ERROR_MESSAGE,
					ErrorCodesAndMessages.PASSWORD_MISMATCH_ERROR_CODE));
		}
	}// for previous old passwords not be matched with new one

	private byte[] hashPassword(byte[] passwordBytes, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			digest.reset();
			digest.update(salt);
			byte[] hashBytes = digest.digest(passwordBytes);
			for (int i = 0; i < iterations - 1; i++) {
				digest.reset();
				hashBytes = digest.digest(hashBytes);
			}
			return hashBytes;
		}
		catch (NoSuchAlgorithmException ex) {
			throw new HashingPasswordException(ex,
					new Errors(TargetTypes.PASSWORD_HASHING_ERROR.name(),
							ErrorCodesAndMessages.PASSWORD_HASHING_ERROR_MESSAGE,
							ErrorCodesAndMessages.PASSWORD_HASHING_ERROR_CODE));
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	private byte[] hexToBytes(String hex) {
		int len = hex.length();
		byte[] bytes = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return bytes;
	}

	private byte[] generateSalt(String saltKey) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			byte[] saltBytes = saltKey.getBytes(StandardCharsets.UTF_8);
			digest.update(saltBytes);
			return digest.digest();
		}
		catch (NoSuchAlgorithmException ex) {
			throw new SaltKeyException(ex,
					new Errors(TargetTypes.SALT_KEY_ERROR.name(),
							ErrorCodesAndMessages.SALT_KEY_GENERATION_ERROR_MESSAGE,
							ErrorCodesAndMessages.SALT_KEY_GENERATION_ERROR_CODE));
		}
	}

}
