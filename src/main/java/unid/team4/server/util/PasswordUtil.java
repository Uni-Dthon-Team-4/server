package unid.team4.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    // 비밀번호 해시 메서드
    public static String hashPassword(String password, byte[] salt) {
        if (password == null || salt == null) {
            throw new IllegalArgumentException("Password and salt must not be null");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);  // salt 추가
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }

    // 솔트 생성 메서드
    public static byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);  // 랜덤 솔트 생성
        return salt;
    }

    public static void main(String[] args) {
        String password = "your_password";
        byte[] salt = generateSalt();  // 솔트 생성
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Salt (Base64): " + Base64.getEncoder().encodeToString(salt));
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
