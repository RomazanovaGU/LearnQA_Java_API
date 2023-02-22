package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import java.util.Map;
@Epic("Registration cases")
@Feature("Registration")
public class UserRegisterTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    private DataGenerator dataGenerator = new DataGenerator();
@Test
@Description("This test check user registration with existing email in DB")
@DisplayName("Test negative registration user")
    public void testCreateUserExistingEmail(){
    String email = "vinkotov@example.com";
    Map<String, String>  nonDefaultEmail = new HashMap<>();
    nonDefaultEmail.put("email",  email);
    Map<String, String> userData = dataGenerator.getRegistrationData(nonDefaultEmail);

    Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);

    Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
}
    @Test
    @Description("This test check new user registration")
    @DisplayName("Test positive registration user")
    public void testCreateUserSuccessfully(){
        String email = DataGenerator.getRandomEmail();
        Map<String, String> userData = dataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }
}
