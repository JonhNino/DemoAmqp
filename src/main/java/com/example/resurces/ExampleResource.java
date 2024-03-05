package com.example.resurces;

import com.example.log.LogEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.reactive.messaging.*;
import org.jboss.logging.Logger;
import org.json.XML;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Path("demo/")
@LogEvent
public class ExampleResource {

    private static Logger log = Logger.getLogger(ExampleResource.class);

    @Channel("ibprosvc-rsdemo")
    Emitter<String> quoteRequestEmitter;
    @Channel("proxy365-rsdemo")
    Emitter<String> quoteRequestEmitterProxy365;

    /**
     * Envio a al topic de consultaProducto

     */
    @Path("/hello")
    @POST
    @Operation(summary = "Adds an Expense", operationId = "createExpense")
    @APIResponse(
            responseCode = "201",
            headers = {
                    @Header(
                            name = "id",
                            description = "ID of the created entity",
                            schema = @Schema(implementation = Integer.class)
                    ),
            },
            description = "Expense successfully created"
    )
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> createExpense(@Context UriInfo uriInfo, String xmlRequestBody) throws JsonProcessingException {
        log.info("Se envio mensjae a la cola ");
        CompletionStage<Void> completionStage = quoteRequestEmitter.send(xmlRequestBody);

        // Convertir CompletionStage a Uni
        return Uni.createFrom().completionStage(completionStage)
                .onItem().transformToUni(ignored ->
                        Uni.createFrom().item(Response.ok("Procesamiento completa2o1").build())
                );
    }
    /**
     * Recibe del topic de consultaProducto

     */
    @Incoming("processed-responsedemo")
    public Uni<Response> receiveProcessedResponse(String processedMessage) {

        log.info("Llego a cola ");
        log.info("Mensaje procesado recibido: " + processedMessage);
        return Uni.createFrom().item(
                Response.ok(processedMessage).build()
        );

    }
    /**
     * Recibe del topic de crearCompania

     */
    @Incoming("LoggerMsTopic")
    public Uni<Response> receiveProcessedResponseLoggerMsTopic(String loggerMsTopic) {

        log.info("Llego a cola ");
        log.info("Mensaje procesado recibido: " + loggerMsTopic);
        return Uni.createFrom().item(
                Response.ok(loggerMsTopic).build()
        );
    }

    /**
     * Envio a al topic de proxy365

     */
    @Path("/proxy365")
    @POST
    @Operation(summary = "Adds an Expense", operationId = "createExpense")
    @APIResponse(
            responseCode = "201",
            headers = {
                    @Header(
                            name = "id",
                            description = "ID of the created entity",
                            schema = @Schema(implementation = Integer.class)
                    ),
            },
            description = "Expense successfully created"
    )
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Response> createProxy365(@Context UriInfo uriInfo, String xmlRequestBody)  {
        log.info("Se envio mensjae a la cola proxy365");
        CompletionStage<Void> completionStage = quoteRequestEmitterProxy365.send(xmlRequestBody);
        // Convertir CompletionStage a Uni
        return Uni.createFrom().completionStage(completionStage)
                .onItem().transformToUni(ignored ->
                        Uni.createFrom().item(Response.ok("Procesamiento completado Proxy365").build())
                );
    }

    /**
     * Recibe del topic de proxy365

     */
    @Incoming("proxy365-responsedemo")
    public Uni<Response> receiveProcessedResponseproxy365(String processedMessage) {

        log.info("Llego a cola  proxy365");
        log.info("Mensaje procesado recibido proxy365: " + processedMessage);
        return Uni.createFrom().item(
                Response.ok(processedMessage).build()
        );

    }

}
