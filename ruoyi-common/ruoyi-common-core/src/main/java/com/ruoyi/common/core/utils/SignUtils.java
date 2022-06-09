package com.ruoyi.common.core.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignUtils {


    /**
     * HmacSHA256 签名
     * @param paramStr 签名参数
     * @param secret 签名密钥
     * @return
     */
    public static String HmacSHA256(String paramStr, String secret) {
        String digest = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = secret.getBytes("UTF-8");
            byte[] dataBytes = paramStr.getBytes("UTF-8");
            SecretKey secretKey = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secretKey);
            byte[] doFinal = mac.doFinal(dataBytes);
            byte[] hexB = new Hex().encode(doFinal);
            digest = new String(hexB, "utf-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return digest;
    }
}
