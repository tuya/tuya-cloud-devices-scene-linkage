package com.tuya.cloud.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: AddAutomationsReq
 * @description: 添加自动化请求内容
 * @author: 布鲁 龙盼盼
 * @date: 2021/4/9
 **/
@Data
public class AddAutomationsReq implements Serializable {
    private String name;
    private String background;
    private List<Condition> conditions;
    private List<Action> actions;
    @JSONField(name = "match_type")
    private Integer matchType;
    @JSONField(name = "condition_rule")
    private String conditionRule;
    private List<Precondition> preconditions;

    public void create(String conditionDeviceId, String actionDeviceId) {
        // TODO 前端获取
        this.name = "自动化测试-布鲁";
        this.background = "https://images.tuyacn.com/smart/rule/cover/bedroom.png";
        this.matchType = 1;
        this.conditions = fetchConditions(conditionDeviceId);
        this.actions = fetchActions(actionDeviceId);
    }

    private List<Condition> fetchConditions(String conditionDeviceId) {
        // TODO 指令集获取
        Condition condition = new Condition();
        condition.setEntityId(conditionDeviceId);
        condition.setEntityType(1);
        condition.setOrderNum(1);
        Condition.Display display = new Condition.Display();
        display.setCode("presence_state");
        display.setOperator("==");
        display.setValue("presence");
        condition.setDisplay(display);
        List<Condition> resultConditions = new ArrayList<>();
        resultConditions.add(condition);
        return resultConditions;
    }

    private List<Action> fetchActions(String actionDeviceId) {
        // TODO 指令集获取
        Action action = new Action();
        action.setEntityId(actionDeviceId);
        action.setActionExecutor("dpIssue");
        JSONObject executorProperty = new JSONObject();
        executorProperty.put("switch", true);
        action.setExecutorProperty(executorProperty);
        List<Action> resultActions = new ArrayList<>();
        resultActions.add(action);
        return resultActions;
    }

    @Data
    public static class Condition implements Serializable {
        @JSONField(name = "entity_type")
        private Integer entityType;
        private Display display;
        @JSONField(name = "order_num")
        private Integer orderNum;
        @JSONField(name = "entity_id")
        private String entityId;

        @Data
        public static class Display implements Serializable {
            private String code;
            private String operator;
            private String value;
        }
    }

    @Data
    public static class Action implements Serializable {
        @JSONField(name = "entity_id")
        private String entityId;
        @JSONField(name = "action_executor")
        private String actionExecutor;
        @JSONField(name = "executor_property")
        private JSON executorProperty;
    }

    @Data
    public static class Precondition implements Serializable {
        @JSONField(name = "cond_type")
        private String condType;
        private Display display;

        @Data
        public static class Display implements Serializable {
            private String start;
            private String end;
            private String loops;
            @JSONField(name = "timezone_id")
            private String timezoneId;
        }
    }

}
