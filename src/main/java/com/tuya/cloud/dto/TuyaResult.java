package com.tuya.cloud.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 结果类
 */
@Data
public class TuyaResult<T> implements Serializable {

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 请求失败信息，成功为空
     */
    private String msg;

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 当前时间戳
     */
    private Long t;

    /**
     * 结果集
     */
    private T result;

    public TuyaResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public TuyaResult(Boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public TuyaResult(Boolean success, Long t, T result) {
        this.success = success;
        this.t = t;
        this.result = result;
    }

    public TuyaResult() {

    }

    @Override
    public String toString() {
        return "TuyaResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", t=" + t +
                ", result=" + result +
                '}';
    }
}
