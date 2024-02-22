package services;

import com.example.dto.RequestHeaders;
import com.example.model.ESBExcepcionMessage;
import com.example.utils.ValidatorUtil;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class UtilsTest {

    @Inject
    ValidatorUtil validatorUtil;
    RequestHeaders requestHeaders;

    @BeforeEach
    void setUp() {
        requestHeaders = new RequestHeaders();
        requestHeaders.setCanal("");
    }

    @Test
    void testHeaders() {
        Assertions.assertThrows(ESBExcepcionMessage.class, ()->{
            validatorUtil.validateHeaders(requestHeaders);
        });
    }
}
