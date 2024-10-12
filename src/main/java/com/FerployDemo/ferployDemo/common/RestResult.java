package com.FerployDemo.ferployDemo.common;

//Status와 데이터를 일정한 형식으로 반환하기 위한 클래스
public class RestResult<T> {
    private String status;
    private T data;

    public RestResult(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}