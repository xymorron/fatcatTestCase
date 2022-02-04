package com.fatcat.nutrition.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponseStatus {

    private boolean result;
    private String message;
    private String error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime timeStamp;

    public ApiResponseStatus() {
        timeStamp = LocalDateTime.now();
    }

    public ApiResponseStatus(boolean result, String message, Throwable ex) {
        this();
        this.result = result;
        this.message = message;
        this.error = ex.getLocalizedMessage();
    }
}