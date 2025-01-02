package com.proj.customer.util;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseStructure<T> {

    private T data;
    private String message;
    private int code;
    private HttpStatus status;
    
    public static <T> ResponseStructure<T> buildResponse(T data, String message, HttpStatus status, int code) {
        ResponseStructure<T> response = new ResponseStructure<>();
        response.setData(data);
        response.setMessage(message);
        response.setCode(code);
        response.setStatus(status);
        return response;
    }
}

