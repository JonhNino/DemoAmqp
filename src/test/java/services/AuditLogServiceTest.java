package services;

import com.example.dto.AuditLogDTO;
import com.example.dto.RequestHeaders;
import com.example.service.AuditLogService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

import static org.mockito.Mockito.*;

@QuarkusTest
class AuditLogServiceTest {

    @Inject
    AuditLogService auditLogService;
    AuditLogDTO auditLogDTO;

    @BeforeEach
    void setUp() {
        auditLogDTO = new AuditLogDTO();
        auditLogDTO.setDuration(Duration.ZERO);
        auditLogDTO.setEnd(
                Instant.now());
        auditLogDTO.setStart(Instant.now());
        auditLogDTO.setRequestHeaders(new RequestHeaders());
    }

    @Test
    void test() {
        AuditLogService auditLogServices = spy(AuditLogService.class);
        auditLogServices.logMessage(auditLogDTO);
        verify(auditLogServices, times(1)).logMessage(auditLogDTO);
    }
}

