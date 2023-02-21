package lecture1_3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeworkCookieCheck {
    @Test
    public void cookieCheck() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        Map<String, String> cookies = response.getCookies();
        assertTrue(cookies.containsKey("HomeWork"), "Responce has unexpected cookie" + cookies);
        if (cookies.containsKey("HomeWork")){
            assertEquals ("hw_value", response.getCookie("HomeWork"), "Responce has unexpected value of cookie" + cookies.values());
        }
    };

}

