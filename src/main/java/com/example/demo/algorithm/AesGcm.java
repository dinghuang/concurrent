package com.example.demo.algorithm;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.*;
import java.util.Arrays;


/**
 * 一种称为认证加密的加密方式，它同时为数据的机密性、完整性和真实性提供了保证。支持此功能最流行的块模式之一为
 * Galois/Counter Mode or GCM（比如它可以使用 TLS v1.2 中的密码组件）。
 * <p>
 * GCM 基于 CTR 模式，它还在加密期间顺序计算身份验证标记。然后该标记通常会附加到密文中。它的大小是一个重要的安全属性，
 * 因此它的长度至少是 128 位。
 * <p>
 * 它还可以验证未包括在明文中的附加信息。该数据称为关联数据。这为什么有用呢？例如，加密数据具有元属性，
 * 即用于检查是否必须重新加载内容的创建日期。攻击者可以轻松更改创建日期，但如果将其添加为关联数据， CGM 将验证此信息并识别出更改。
 *
 * @author dinghuang123@gmail.com
 * @since 2018/12/27
 */
public class AesGcm {

    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        decodePassword("123456");
    }


    private static void decodePassword(String password) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE, random);
        SecretKey key = keyGen.generateKey();
        System.out.println("秘钥" + Arrays.toString(key.getEncoded()));
        // ===============加密
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        final byte[] nonce = new byte[GCM_NONCE_LENGTH];
        random.nextBytes(nonce);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, nonce);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] aad = "额外信息".getBytes();
        cipher.updateAAD(aad);
        byte[] cipherText = cipher.doFinal(password.getBytes());
        System.out.println("秘文" + Arrays.toString(cipherText));
        // =================解密
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        cipher.updateAAD(aad);
        byte[] plainText = cipher.doFinal(cipherText);
        // check if the decryption result matches
        if (Arrays.equals(password.getBytes(), plainText)) {
            System.out.println("Test Passed: match!");
        } else {
            System.out.println("Test Failed: result mismatch!");
            System.out.println(new String(plainText));
        }
    }

}
