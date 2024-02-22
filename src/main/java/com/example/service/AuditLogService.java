package com.example.service;

import com.example.dto.AuditLogDTO;
import com.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@ApplicationScoped
public class AuditLogService {

    @Inject
    bpd.microservice.common.auditlogmqutility.extension.runtime.AuditLogService auditLogServiceExtension;

    private Gson gson = new Gson();
    private static Logger logger = LoggerFactory.getLogger(AuditLogService.class);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.YYYYMMDDHHMMSS)
            .withZone(ZoneId.systemDefault());

    public void logMessage(AuditLogDTO auditLogDTO) {
        logger.info("Start log message method");
        String logHelper = new Gson().toJson(auditLogDTO);
        logger.debug("*** AuditLogDto ***\r\n{}", logHelper);

        try {
            Optional<JsonElement> headersJsonElement = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getRequestHeaders).map(gson::toJsonTree);
            Optional<JsonElement>  requestJsonElement = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getRequestDTO).map(gson::toJsonTree);
            Optional<JsonElement>  responseJsonElement = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getResponseDTO).map(gson::toJsonTree);

            Optional<String> startTime = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getStart).map(DATE_TIME_FORMATTER::format);
            Optional<String> endTime = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getEnd).map(DATE_TIME_FORMATTER::format);
            Optional<String> elapsedTime = Optional.ofNullable(auditLogDTO).map(AuditLogDTO::getDuration).map(Duration::getSeconds).map(time->Long.toString(time));

            auditLogServiceExtension.logMessage(responseJsonElement, headersJsonElement, requestJsonElement, startTime, endTime, elapsedTime, Constants.MS_NAME);

        } catch (Exception e) {
            logger.error("[LoginService] something went wrong when sending AuditLogingMessage", e);
        }
    }
}
