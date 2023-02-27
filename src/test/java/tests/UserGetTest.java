package tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



@Epic("EP-210: Get User cases")
@Story("CORP-111: Get User")
public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Links ({@Link( name="TC1", url="https://playground.learnqa.ru"),@Link (name = "Story-124", url="https://playground.learnqa.ru")})
    @Severity(value = SeverityLevel.CRITICAL)
    @Description("This test checks that non-auth request gets username only")
    @DisplayName("Test positive non-auth request gets username only")
    public void testGetUserDataNotAuth(){
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }
@Test
@Links ({@Link( name="TC2", url="https://playground.learnqa.ru"),@Link (name = "Story-124", url="https://playground.learnqa.ru")})
@Description("This test checks that auth request gets all params")
@Severity(value = SeverityLevel.NORMAL)
@DisplayName("Test positive auth request gets all params")
    public void testGetUserDetailsAuthAsSameUser(){
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/2", header, cookie);
        String [] expectedFields = {"username", "firstName", "lastName", "email",};
        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }
    @Test
    @Links ({@Link( name="TC3", url="https://playground.learnqa.ru"),@Link (name = "Story-124", url="https://playground.learnqa.ru")})
    @Severity(value = SeverityLevel.NORMAL)
    @Description("This test checks that auth request of another user gets username only")
    @DisplayName("Test positive auth request of another user gets username only")
    public void testGetUserDetailsAuthAsAnotherUser(){
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/62814", header, cookie);
        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
    }
}
