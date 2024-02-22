package com.example.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotEmpty;

@JsonAutoDetect
@JsonDeserialize
public class RequestHeaders {


    @NotEmpty
    private String canal;

    @NotEmpty
    private String authorization;

    private String backendRequest;
    private String backendResponse;


    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }



    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getBackendRequest() {
        return backendRequest;
    }

    public void setBackendRequest(String backendRequest) {
        this.backendRequest = backendRequest;
    }

    public String getBackendResponse() {
        return backendResponse;
    }

    public void setBackendResponse(String backendResponse) {
        this.backendResponse = backendResponse;
    }

    @Override
    public String toString() {
        return "RequestHeaders [servicio="+ ", operacion=" + ", canal=" + canal + ", correlId="
                +  ", tipoDocumento=" +  ", numeroDocumento="
                + ", backendRequest=" + backendRequest + ", backendResponse=" + backendResponse + "]";
    }

}