import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkHeaderCheck {
    @Test
    public void headersCheck() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"), "Headers has unexpected name" + headers);
        if (headers.hasHeaderWithName("x-secret-homework-header")){
            assertEquals ("Some secret value", headers.getValue("x-secret-homework-header"), "Headers has unexpected value " +  headers);
        }
    };
}
