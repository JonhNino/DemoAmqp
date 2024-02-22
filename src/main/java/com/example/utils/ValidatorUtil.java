package com.example.utils;

import com.example.dto.RequestHeaders;
import com.example.model.ESBExcepcionMessage;
import com.example.model.ESBExcepcionType;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class ValidatorUtil {


    @Inject
    Validator validator;

    /**
     * Validate required Request Headers
     *
     * @param requestHeaders
     * @throws ValidationException
     */
    public void validateHeaders(RequestHeaders requestHeaders) throws ESBExcepcionMessage {
        Set<ConstraintViolation<RequestHeaders>> violations = validator.validate(requestHeaders);
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append(Constants.REQUIRED_HEADERS_MISSING);
            message.append(". ");
            message.append(
                    violations.stream().map(cv -> cv.getPropertyPath().toString().concat(": ").concat(cv.getMessage()))
                            .collect(Collectors.joining(", ")));

            throw new ESBExcepcionMessage(message.toString(), new ESBExcepcionType("400", message.toString()));
        }
    }

}

