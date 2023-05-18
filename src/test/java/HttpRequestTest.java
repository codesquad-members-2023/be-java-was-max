import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Test
    @DisplayName("requestheader, request body 구분")
    void test(){
        String blank = "";
        logger.debug("");
    }
}
