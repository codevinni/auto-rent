package br.autorent.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * Utilitário para hashing e verificação de senhas usando SHA-256.
 */
public class PasswordUtils {
	
	/** Construtor privado para impedir instanciação. */
	private PasswordUtils() {}
	
	/**
	 * Gera o hash SHA-256 de um texto em formato hexadecimal.
	 *
	 * @param plainText texto a ser hasheado
	 * @return hash SHA-256 em hexadecimal
	 * @throws RuntimeException se ocorrer erro no algoritmo de hash
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
     * Verifica se um texto corresponde a um hash armazenado.
     *
     * @param plainText texto para comparação
     * @param storedHash hash armazenado para verificação
     * @return {@code true} se o hash do texto corresponder ao hash armazenado
     */
    public static boolean checkPassword(String plainText, String storedHash) {
        return hash(plainText).equalsIgnoreCase(storedHash);
    }
}