package com.tuya.cloud.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: HeaderUtils
 * @description: 消息头处理工具类
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
public class HeaderUtils {
    public static Map<String, Object> fetchHeaders(String clientId, String secret) throws Exception {
        return fetchHeaders(clientId, secret, null);
    }

    public static Map<String, Object> fetchHeaders(String clientId, String secret, String accessToken) throws Exception {
        long timestamp = System.currentTimeMillis();
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("client_id", clientId);
        headerMap.put("t", timestamp);
        headerMap.put("sign_method", "HMAC-SHA256");
        String message = clientId + timestamp;
        if (accessToken != null) {
            headerMap.put("access_token", accessToken);
            message = clientId + accessToken + timestamp;
        }
        headerMap.put("sign", structureSign(message, secret));
        return headerMap;
    }

    private static String structureSign(String message, String secret) throws Exception {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] bytes = sha256HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return new HexBinaryAdapter().marshal(bytes).toUpperCase();
    }

}
