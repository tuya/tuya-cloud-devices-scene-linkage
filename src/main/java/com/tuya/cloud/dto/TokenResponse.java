package com.tuya.cloud.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * token响应类
 */
@Data
public class TokenResponse implements Serializable {

    /**
     * access_token
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * refresh_token
     */
    @JSONField(name = "refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JSONField(name = "expire_time")
    private long expireTime;

    /**
     * 用户id
     */
    private String uid;

}
