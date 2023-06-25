package com.example.springProject.assignment.common;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class ApiCallResp {


    private String body;
    private HttpStatus httpStatus;

    private String error;

    public ApiCallResp(String body, String error, HttpStatus httpStatus) {
        this.body = body;
        this.error = error;
        this.httpStatus = httpStatus;
    }


    public ApiCallResp(String body, HttpStatus httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

}
