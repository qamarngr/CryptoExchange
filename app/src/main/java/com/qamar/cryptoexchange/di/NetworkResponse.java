package com.qamar.cryptoexchange.di;

public class NetworkResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public NetworkResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
