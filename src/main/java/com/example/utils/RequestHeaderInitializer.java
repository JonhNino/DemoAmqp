package com.example.utils;

import com.example.dto.RequestHeaders;
import com.example.exceptions.ExceptionUtility;
import com.example.model.ESBExcepcionMessage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Singleton
@Slf4j
public class RequestHeaderInitializer {
    private RequestHeaders requestHeaders;

    public RequestHeaders setRequestHeaders(HttpHeaders httpHeaders) throws ESBExcepcionMessage {
        requestHeaders = new RequestHeaders();
        try {

            if (httpHeaders.getRequestHeaders().containsKey(Constants.HEADER_AUTHORIZATION))
                requestHeaders.setAuthorization(httpHeaders.getHeaderString(Constants.HEADER_AUTHORIZATION));
            else
                requestHeaders.setAuthorization(Constants.EMPTY_STRING);

            if (httpHeaders.getRequestHeaders().containsKey(Constants.CANAL))
                requestHeaders.setCanal(httpHeaders.getHeaderString(Constants.CANAL));
            else
                requestHeaders.setCanal(Constants.EMPTY_STRING);


        } catch (Exception ex) {
            throw ExceptionUtility.prepareESBExceptExcepcionMessage(Constants.ERROR_CODE_6099, Constants.REQUIRED_HEADERS_MISSING, null);
        }
        return requestHeaders;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void verifyRequestHeaders(RequestHeaders requestHeaders) throws ESBExcepcionMessage {
        log.info("Validation Request Header...");
        List<String> list = new ArrayList<>();

        if (requestHeaders.getCanal().isEmpty()) {
            list.add(Constants.CANAL);
        }
        if (requestHeaders.getAuthorization().isEmpty()) {
            list.add(AUTHORIZATION);
        }
        if (!list.isEmpty()) {
            String msg = this.headersMissing(list);
            throw ExceptionUtility.prepareESBExceptExcepcionMessage(Constants.ERROR_CODE_400, Constants.REQUIRED_HEADERS_MISSING + msg, null);
        }
    }

    private String headersMissing(List<String> list) {
        StringBuilder sb = new StringBuilder(". Cause(s): ");
        if (list.size() > 1) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i));
                if (i == list.size() - 2) sb.append(" and ");
                else if (i == list.size() - 1) sb.append(" ");
                else sb.append(", ");
            }
            sb.append("headers are required.");
        } else {
            sb.append("the ");
            sb.append(list.get(0));
            sb.append(" header is required.");
        }
        return sb.toString();
    }
}

