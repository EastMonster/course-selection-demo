package io.github.eastmonster.dbsim.util;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private Boolean success;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    private Result() {}

    public static Result ok() {
        return new Result()
                .setSuccess(true);
    }

    public static Result error() {
        return new Result()
                .setSuccess(false);
    }

    public Boolean isSuccess() {
        return success;
    }

    public Result setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Result addData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public Result addData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
