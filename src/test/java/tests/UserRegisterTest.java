package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
@Description("This test check user registration with existing email")
@DisplayName("Test negative registration user with existing email")
    public void testCreateUserExistingEmail(){
    String email = "vinkotov@example.com";
    Map<String, String>  existingEmail = new HashMap<>();
    existingEmail.put("email",  email);
    Map<String, String> userData = dataGenerator.getRegistrationData(existingEmail);

    Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);

    Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    Assertions.assertResponseTextEquals(responseCreateAuth, "Users with email '" + email + "' already exists");
}

    @Test
    @Description("This test check user registration with invalid email")
    @DisplayName("Test negative registration user with invalid email")
    public void testCreateUserInvalidEmail() {
        String email = "vinkotovexample.com";
        Response responseForCheck = apiCoreRequests.makePostRequestWithInvalidEmailParam("https://playground.learnqa.ru/api/user/", email);

        Assertions.assertResponseCodeEquals(responseForCheck, 400);
        Assertions.assertResponseTextEquals(responseForCheck, "Invalid email format");
    }
    @Description("This test check user registration with long name")
    @DisplayName("Test negative user registration  with long name")
    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName"})
    public void testCreateUserLongName(String condition) {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/");
        if (condition.equals("username")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithLongNameParam("https://playground.learnqa.ru/api/user/", "username");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'username' field is too long");
        } else if (condition.equals("firstName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithLongNameParam("https://playground.learnqa.ru/api/user/", "firstName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'firstName' field is too long");
        } else if (condition.equals("lastName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithLongNameParam("https://playground.learnqa.ru/api/user/", "lastName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'lastName' field is too long");
        } else {
            throw new IllegalArgumentException("Condition value is unknown " + condition);
        }
    }
    @Description("This test check user registration with short name")
    @DisplayName("Test negative user registration  with short name")
    @ParameterizedTest
    @ValueSource(strings = {"username", "firstName", "lastName"})
    public void testCreateUserShortName(String condition) {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/");
        if (condition.equals("username")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithShortNameParam("https://playground.learnqa.ru/api/user/", "username");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'username' field is too short");
        } else if (condition.equals("firstName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithShortNameParam("https://playground.learnqa.ru/api/user/", "firstName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'firstName' field is too short");
        } else if (condition.equals("lastName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithShortNameParam("https://playground.learnqa.ru/api/user/", "lastName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The value of 'lastName' field is too short");
        } else {
            throw new IllegalArgumentException("Condition value is unknown " + condition);
        }
    }
    @Description("This test check user registration with missed parameter")
    @DisplayName("Test negative user registration  with missed parameter")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void testCreateUserMissedParam(String condition) {
        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/");

        if (condition.equals("email")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithMissedParam("https://playground.learnqa.ru/api/user/", "email");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: email");
        } else if (condition.equals("password")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithMissedParam("https://playground.learnqa.ru/api/user/", "password");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: password");
        } else if (condition.equals("username")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithMissedParam("https://playground.learnqa.ru/api/user/", "username");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: username");
        } else if (condition.equals("firstName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithMissedParam("https://playground.learnqa.ru/api/user/", "firstName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: firstName");
        } else if (condition.equals("lastName")) {
            Response responseForCheck = apiCoreRequests.makePostRequestWithMissedParam("https://playground.learnqa.ru/api/user/", "lastName");
            Assertions.assertResponseCodeEquals(responseForCheck, 400);
            Assertions.assertResponseTextEquals(responseForCheck, "The following required params are missed: lastName");
        } else {
            throw new IllegalArgumentException("Condition value is unknown " + condition);
        }
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
