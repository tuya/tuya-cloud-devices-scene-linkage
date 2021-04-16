package com.tuya.cloud.controller;

import com.tuya.cloud.service.AutomationService;
import com.tuya.cloud.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: AutomationsEnableLinkageCtrl
 * @description: 自动化场景联动API
 * @author: bulu
 * @date: 2021/4/8
 **/
@RestController
@Slf4j
public class AutomationsEnableLinkageCtrl {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AutomationService automationService;

    @GetMapping(value = "/")
    public String root() {
        return "welcome AutomationsEnableLinkageCtrl";
    }

    /**
     * @param: homeId 家庭ID eg:3170***
     * @param: conditionDeviceId 条件设备的ID eg:6cdad3c725******
     * @param: actionDeviceId 动作设备的ID eg:1011201084f3e******
     * @version 1.0
     * @description: TODO 自动化配置场景联动
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼
     * @date: 2021/4/8
     */
    @PostMapping(value = "/v1.0/automations")
    public String enableAutomations(@RequestParam("homeId") String homeId,
                                    @RequestParam("conditionDeviceId") String conditionDeviceId,
                                    @RequestParam("actionDeviceId") String actionDeviceId
    ) {

        try {
            // 获取access_token
            String accessToken = tokenService.fetchAccessToken();
            // 添加自动化
            String autoId = automationService.addAutomations(accessToken, homeId, conditionDeviceId, actionDeviceId);
            // 启动自动化
            boolean startFlag = automationService.startAutomations(accessToken, homeId, autoId);

            // TODO 定义统一成功内容输入
            return "automations start flag:" + startFlag + " autoId:" + autoId;
        } catch (Exception e) {
            log.error("automations start failure.", e);
            // TODO 定义统一异常内容输出
            throw new RuntimeException("automations start inner failure.");
        }
    }

    /**
     * @param homeId 家庭ID eg:3170****
     * @param autoId 自动化ID eg:oN6CRjAKM****
     * @version 1.0
     * @description: 自动化配置场景联动
     * @return: java.lang.String
     * @author: 布鲁 龙盼盼
     * @date: 2021/4/8
     */
    @DeleteMapping(value = "/v1.0/automations")
    public String deleteAutomations(@RequestParam("homeId") String homeId, @RequestParam("autoId") String autoId) {

        try {
            // 获取access_token
            String accessToken = tokenService.fetchAccessToken();
            // 启动自动化
            boolean startFlag = automationService.deleteAutomations(accessToken, homeId, autoId);

            return "automations delete flag:" + startFlag;
        } catch (Exception e) {
            log.error("automations delete failure.", e);
            throw new RuntimeException("automations delete inner failure.");
        }
    }
}
