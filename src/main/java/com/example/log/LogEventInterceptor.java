package com.example.log;

import com.example.dto.AuditLogDTO;
import com.example.dto.RequestHeaders;
import com.example.dto.ResponseDTO;
import com.example.model.ESBExcepcionMessage;
import com.example.service.AuditLogService;
import com.example.utils.RequestHeaderInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;

@LogEvent
@Interceptor
public class LogEventInterceptor {

    private static Logger logger = LoggerFactory.getLogger(LogEventInterceptor.class);

    @Inject
    AuditLogService auditLogService;

    @Inject
    RequestHeaderInitializer requestHeaderInitializer;

    @AroundInvoke
    public Object logEvent(InvocationContext ctx) throws Exception {
        Instant start = Instant.now();
        Object response = null;
        Object request = null;
        try {
            for (Object param : ctx.getParameters()) {
                if (param instanceof ResponseDTO)
                    request = param;
                else if(param instanceof RequestHeaders){
                    request = ((RequestHeaders) param).getBackendRequest();
                    response = ((RequestHeaders) param).getBackendResponse();
                }
                else if(param instanceof String){
                    request = param;
                    response = param;
                }
            }
            response = ctx.proceed();
            return response;
        } catch (ESBExcepcionMessage esbExcepcionMessage) {
            ResponseDTO dto = new ResponseDTO();
            dto.setErrorCode(esbExcepcionMessage.getFaultInfo().getCodigo());
            dto.setErrorDescription(esbExcepcionMessage.getFaultInfo().getDescripcion());
            logger.error("Error in logEvent", esbExcepcionMessage);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(dto)
                    .build();
        } finally {

            if(ctx.getParameters() != null) {
                for (Object param : ctx.getParameters()) {
                    if (param instanceof RequestHeaders) {
                        request = ((RequestHeaders) param).getBackendRequest();
                        response = ((RequestHeaders) param).getBackendResponse();
                    }
                }
            }
            Object finalRequest = request;
            Object finalResponse = response;

            Uni.createFrom()
                    .voidItem()
                    .emitOn(Infrastructure.getDefaultWorkerPool())
                    .subscribe()
                    .with(unused -> {
                        requestAuditLog(start, finalRequest, finalResponse);
                    }, Throwable::printStackTrace);
        }
    }

    private Uni<Void> requestAuditLog(Instant start, Object request, Object response) {
        var auditLogDTO = new AuditLogDTO();
        var requestHeaders = requestHeaderInitializer.getRequestHeaders();

        auditLogDTO.setRequestHeaders(requestHeaders);
        var gson = new Gson();
        try {
            if (request instanceof ResponseDTO)
                auditLogDTO.setRequestDTO((ResponseDTO) request);
            if (response instanceof Response) {
                var responseUserData = (ResponseDTO) ( (Response) response).getEntity();
                responseUserData.setErrorCode(responseUserData.getErrorCode());
                auditLogDTO.setResponseDTO(responseUserData);
            }
            if(request instanceof String){
                request = ((String) request).replace("\"", "\\\"");
                request = "{\"content\":\"" + request + "\"}";
                auditLogDTO.setRequestDTO(gson.fromJson(request.toString(), JsonElement.class));
            }
            if(response instanceof String){
                response = ((String) response).replace("\"", "\\\"");
                response = "{\"content\":\"" + response + "\"}";
                auditLogDTO.setResponseDTO(gson.fromJson(response.toString(), JsonElement.class));
            }

        } catch (Exception e) {
            logger.error("Error while clone the request or the response", e);
        }

        auditLogDTO.setStart(start);
        Instant end = Instant.now();
        auditLogDTO.setEnd(end);
        auditLogDTO.setDuration(Duration.between(start, end));
        logMessage(auditLogDTO);
        return Uni.createFrom().voidItem();
    }

    private void logMessage(AuditLogDTO auditLogDTO) {
        // @formatter:off
        Uni.createFrom()
                .item(auditLogDTO)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .subscribe()
                .with(this::processLogMessage, Throwable::printStackTrace);
        // @formatter:on
    }

    private Uni<Void> processLogMessage(AuditLogDTO auditLogDTO) {
        auditLogService.logMessage(auditLogDTO);
        return Uni.createFrom().voidItem();
    }
}
