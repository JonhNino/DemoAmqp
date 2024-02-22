package com.example.model;


public class ESBExcepcionMessage extends Exception {

    private static final long serialVersionUID = 1L;

    private final ESBExcepcionType faultInfo;

    public ESBExcepcionMessage(String message, ESBExcepcionType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public ESBExcepcionType getFaultInfo() {
        return faultInfo;
    }
}
