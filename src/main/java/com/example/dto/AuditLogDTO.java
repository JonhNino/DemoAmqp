package com.example.dto;

import java.time.Duration;
import java.time.Instant;

public class AuditLogDTO {

    private RequestHeaders requestHeaders;
    private Instant start;
    private Instant end;
    private Duration duration;
    private Object responseDTO;
    private Object requestDTO;

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(RequestHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Object getResponseDTO() {
        return responseDTO;
    }

    public void setResponseDTO(Object responseDTO) {
        this.responseDTO = responseDTO;
    }

    public Object getRequestDTO() {
        return requestDTO;
    }

    public void setRequestDTO(Object requestDTO) {
        this.requestDTO = requestDTO;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}

