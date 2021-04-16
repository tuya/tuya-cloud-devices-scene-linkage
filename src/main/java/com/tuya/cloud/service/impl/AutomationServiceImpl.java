package com.tuya.cloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tuya.cloud.configuration.AutomationConfig;
import com.tuya.cloud.configuration.BaseConfig;
import com.tuya.cloud.dto.AddAutomationsReq;
import com.tuya.cloud.dto.TuyaResult;
import com.tuya.cloud.service.AutomationService;
import com.tuya.cloud.utils.HeaderUtils;
import com.tuya.cloud.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @className: EnableAutomationLinkageServiceImpl
 * @description: 自动化相关业务接口
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
@Slf4j
@Service
public class AutomationServiceImpl implements AutomationService {

    @Autowired
    private BaseConfig config;
    @Autowired
    private AutomationConfig automationConfig;

    /**
     * @param: accessToken
     * @param: homeId
     * @param: conditionDeviceId
     * @param: actionDeviceId
     * @description: 添加自动化
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    @Override
    public String addAutomations(String accessToken, String homeId, String conditionDeviceId, String actionDeviceId) {

        try {
            Map<String, Object> headers = HeaderUtils.fetchHeaders(config.getClientId(), config.getSecret(), accessToken);
            String url = config.getDomain() + String.format(automationConfig.getAddUri(), homeId);

            String response = HttpClientUtils.post(url, headers, packageJson(conditionDeviceId, actionDeviceId));
            TuyaResult result = JSONObject.parseObject(response, TuyaResult.class);
            if (!result.getSuccess()) {
                throw new RuntimeException("result is failure." + result.getMsg());
            }
            log.info("fetchAccessToken, return automations id:{}", result.getResult());
            return result.getResult().toString();
        } catch (Exception e) {
            throw new RuntimeException("addAutomations failure." + e);
        }
    }

    /**
     * @param: homeId
     * @param: conditionDeviceId
     * @param: actionDeviceId
     * @description: 包装添加自动化请求
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    private String packageJson(String conditionDeviceId, String actionDeviceId) {
        AddAutomationsReq req = new AddAutomationsReq();
        req.create(conditionDeviceId, actionDeviceId);
        log.info("packageJson, return req:{}", JSONObject.toJSONString(req));
        return JSONObject.toJSONString(req);
    }

    /**
     * @param: accessToken
     * @param: homeId
     * @param: autoId
     * @description: 启动自动化
     * @return: 是否启动成功
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    @Override
    public boolean startAutomations(String accessToken, String homeId, String autoId) {
        try {
            Map<String, Object> headers = HeaderUtils.fetchHeaders(config.getClientId(), config.getSecret(), accessToken);
            String url = config.getDomain() + String.format(automationConfig.getEnableUri(), homeId, autoId);
            String response = HttpClientUtils.put(url, headers);
            TuyaResult result = JSONObject.parseObject(response, TuyaResult.class);
            if (!result.getSuccess()) {
                throw new RuntimeException("startAutomations,result is failure.");
            }
            log.info("startAutomations, start result:{}", result.getResult());
            return (Boolean) result.getResult();
        } catch (Exception e) {
            throw new RuntimeException("startAutomations failure.");
        }
    }

    /**
     * @param: accessToken
     * @param: homeId
     * @param: autoId
     * @description: 启动自动化
     * @return: 是否删除成功
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    @Override
    public boolean deleteAutomations(String accessToken, String homeId, String autoId) throws Exception {
        Map<String, Object> headers = HeaderUtils.fetchHeaders(config.getClientId(), config.getSecret(), accessToken);
        String url = config.getDomain() + String.format(automationConfig.getDeleteUri(), homeId, autoId);
        String response = HttpClientUtils.delete(url, headers);
        TuyaResult result = JSONObject.parseObject(response, TuyaResult.class);
        if (!result.getSuccess()) {
            throw new RuntimeException("deleteAutomations,result is failure.");
        }
        log.info("deleteAutomations, delete result:{}", result.getResult());
        return (Boolean) result.getResult();
    }
}
