package br.autorent.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * 
 */
public class PasswordUtils {
	
	private PasswordUtils() {}
	
	/**
	 * 
	 * @param plainText
	 * @return
	 */
    public static String hash(String plainText) {
    	
        try {
        	
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
            
            return HexFormat.of().formatHex(hashBytes);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    /**
     * 
     * @param plainText
     * @param storedHash
     * @return
     */
    public static boolean checkPassword(String plainText, String storedHash) {
        return hash(plainText).equalsIgnoreCase(storedHash);
    }
}