package com.tuya.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuya.cloud.configuration.BaseConfig;
import com.tuya.cloud.configuration.TokenConfig;
import com.tuya.cloud.dto.TokenResponse;
import com.tuya.cloud.dto.TuyaResult;
import com.tuya.cloud.service.TokenService;
import com.tuya.cloud.utils.HeaderUtils;
import com.tuya.cloud.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: TokenServiceImpl
 * @description: token相关业务接口
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private BaseConfig config;
    @Autowired
    private TokenConfig tokenConfig;

    /**
     * @description: 获取access_token
     * @return: access_token
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    @Override
    public String fetchAccessToken() {
        try {
            Map<String, Object> headers = HeaderUtils.fetchHeaders(config.getClientId(), config.getSecret());
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "1");
            String tokenResponse = HttpClientUtils.get(config.getDomain() + tokenConfig.getTokenUri(), headers, params);
            TuyaResult result = JSONObject.parseObject(tokenResponse, TuyaResult.class);
            if (!result.getSuccess()) {
                throw new RuntimeException("result is failure.");
            }
            TokenResponse resp = JSONObject.parseObject(JSONObject.toJSONString(result.getResult()), TokenResponse.class);
            log.info("fetchAccessToken, return token:{}", resp.getAccessToken());
            return resp.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException("fetch token failure." + e);
        }
    }
}
