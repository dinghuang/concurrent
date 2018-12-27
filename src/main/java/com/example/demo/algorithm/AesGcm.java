package com.example.demo.algorithm;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //=============================加密部分=============================
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        SecretKey secretKey = new SecretKeySpec(key, "AES");
        //NEVER REUSE THIS IV WITH SAME KEY
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //128 bit auth tag length
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        //如果需要，添加可选的关联数据（例如元数据）
//        cipher.updateAAD(new byte[]{1, 2, 3});
        //加密；如果你正在加密大块数据，请研究 CipherInputStream，这样整个内容就无需加载到堆中。
        byte[] cipherText = cipher.doFinal(key);
        //现在将所有内容连接到一条消息。
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);
        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        byte[] cipherMessage = byteBuffer.array();
        //最佳事件是尽可能快地从内存中擦除加密密钥或 IV 等敏感数据。由于 Java 是一种具有自动内存管理的语言，因此我们无法保证以下内容能够预期工作，但在大多数情况下应该如此：
//        Arrays.fill(key, (byte) 0);
        //=============================解密部分=============================
        decode(cipherMessage, key);
    }

    public static void decode(byte[] cipherMessage, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        ByteBuffer byteBufferDecode = ByteBuffer.wrap(cipherMessage);
        int ivLength = byteBufferDecode.getInt();
        // check input parameter
        if (ivLength < 12 || ivLength >= 16) {
            throw new IllegalArgumentException("invalid iv length");
        }
        byte[] ivDecode = new byte[ivLength];
        byteBufferDecode.get(ivDecode);
        byte[] cipherTextDecode = new byte[byteBufferDecode.remaining()];
        byteBufferDecode.get(cipherTextDecode);
        //初始化密码并添加可选的关联数据并解密：
        final Cipher cipherDecode = Cipher.getInstance("AES/GCM/NoPadding");
        cipherDecode.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, ivDecode));
//        cipherDecode.updateAAD(new byte[]{1, 2, 3});
        byte[] plainText = cipherDecode.doFinal(cipherTextDecode);
        System.out.println(Arrays.toString(plainText));
    }
}
