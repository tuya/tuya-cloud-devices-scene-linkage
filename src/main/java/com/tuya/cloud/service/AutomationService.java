package com.tuya.cloud.service;

/**
 * @version: TODO
 * @className: ConfigAutomationLinkageService
 * @description: TODO 类描述
 * @author: 布鲁 龙盼盼 
 * @date: 2021/4/8
 **/
public interface AutomationService {

    /**
     * @param: accessToken
     * @param: homeId
     * @version v1.0
     * @description: 添加自动化
     * @return: 自动化ID
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    String addAutomations(String accessToken, String homeId, String conditionDeviceId, String actionDeviceId);

    /**
     * @param: accessToken
     * @param: homeId
     * @param: autoId
     * @version v1.0
     * @description: 启动自动化
     * @return:
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    boolean startAutomations(String accessToken, String homeId, String autoId);

    /**
     * @param: accessToken
     * @param: homeId
     * @param: autoId
     * @version v1.0
     * @description: 启动自动化
     * @return:
     * @author: 布鲁 龙盼盼 
     * @date: 2021/4/8
     */
    boolean deleteAutomations(String accessToken, String homeId, String autoId) throws Exception;
}
