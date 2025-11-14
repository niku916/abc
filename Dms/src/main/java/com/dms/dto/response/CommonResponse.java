package com.dms.dto.response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonResponse<T> {
    private String responseType;
    private T response;
    private HttpStatus responseCode;
    private boolean IsError;

}