package com.example.exceptions;

import com.example.model.ESBExcepcionMessage;
import com.example.model.ESBExcepcionType;
import com.example.utils.Constants;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtility {

    private static Logger logger = LoggerFactory.getLogger(ExceptionUtility.class);

    private ExceptionUtility() {
        logger.info("ExceptionUtility");
    }

    public static ESBExcepcionMessage prepareESBExceptExcepcionMessage(String codigo, String description, Exception parentException) {
        if (parentException != null) {
            logger.error(parentException.getMessage(), parentException);
        }
        ESBExcepcionType esbExcepcionType = new ESBExcepcionType();
        esbExcepcionType.setCodigo(codigo);
        esbExcepcionType.setDescripcion(description);
        return new ESBExcepcionMessage(parentException != null ? parentException.getMessage() : Constants.STD_ERROR_MSG, esbExcepcionType);
    }

    public static ESBExcepcionMessage prepareNotFoundESBExceptExcepcionMessage(String description, Exception parentException) {
        if (parentException != null) {
            logger.error(parentException.getMessage(), parentException);
        }
        ESBExcepcionType esbExcepcionType = new ESBExcepcionType();
        esbExcepcionType.setCodigo(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
        esbExcepcionType.setDescripcion(description);
        return new ESBExcepcionMessage(parentException != null ? parentException.getMessage() : Constants.STD_ERROR_MSG, esbExcepcionType);
    }
}
