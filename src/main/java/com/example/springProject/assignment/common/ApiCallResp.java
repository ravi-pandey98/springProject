package com.example.springProject.assignment.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiCallResp<T> {


    private T body;

    public ApiCallResp(T body, String error, HttpStatus httpStatus) {
        this.body = body;
        this.error = error;
        this.httpStatus = httpStatus;
    }

    private String error;

    public ApiCallResp(T body, HttpStatus httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    private HttpStatus httpStatus;
}
