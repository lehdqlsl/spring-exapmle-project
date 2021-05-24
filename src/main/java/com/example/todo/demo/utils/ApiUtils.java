package com.example.todo.demo.utils;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response);
    }

    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(Map<String,String> message, HttpStatus status) {
        return new ApiResult<>(false, new ApiError(message, status));
    }

    public static class ApiError {
        private final Map<String,String> error;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String sMessage, HttpStatus status) {
            error = new HashMap<>();
            error.put("error",sMessage);
            this.status = status.value();
        }

        ApiError(Map<String,String> message, HttpStatus status) {
            this.error = message;
            this.status = status.value();
        }

        public Map<String,String> getMessage() {
            return error;
        }

        public int getStatus() {
            return status;
        }

        @Override
        public String toString() {
            HashMap<String, Object> ret = new HashMap<>();
            ret.put("error", error);
            ret.put("status", status);
            return ret.toString();
        }
    }

    public static class ApiResult<T> {
        private final boolean success;
        private final T response;

        private ApiResult(boolean success, T response) {
            this.success = success;
            this.response = response;
        }

        public boolean isSuccess() {
            return success;
        }

        public T getResponse() {
            return response;
        }

        @Override
        public String toString() {
            HashMap<String, Object> ret = new HashMap();
            ret.put("success", success);
            ret.put("response", response);
            return ret.toString();
        }
    }

}