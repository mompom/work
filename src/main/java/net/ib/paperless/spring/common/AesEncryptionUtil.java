package net.ib.paperless.spring.common;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesEncryptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(AesEncryptionUtil.class);
    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "VGrJDyiv4ewd2HTYmV2AcQ==";

    public static String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

            byte[] key = new byte[16];
            int i = 0;

            for (byte b : SECRET_KEY.getBytes(StandardCharsets.UTF_8)) {
                key[i++ % 16] ^= b;
            }

            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, AES_ALGORITHM));

            return new String(Hex.encodeHex(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)))).toUpperCase();
        } catch (Exception e) {
            logger.error("AES encrypt ERROR : {}", e.getMessage());
            return plainText;
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

            byte[] key = new byte[16];
            int i = 0;

            for (byte b : SECRET_KEY.getBytes(StandardCharsets.UTF_8)) {
                key[i++ % 16] ^= b;
            }

            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES_ALGORITHM));

            return new String(cipher.doFinal(Hex.decodeHex(encryptedText.toCharArray())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AES decrypt ERROR : {}", e.getMessage());
            return encryptedText;
        }
    }
}
