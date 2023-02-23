package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import  org.junit.jupiter.api.DisplayName;

@Epic("Edit user cases")
@Feature("Edit user")
public class UserEditTest extends BaseTestCase {
    String cookie;
    String header;
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test checks ability to edit data for new created user")
    @DisplayName("Test positive edit user data")
    public void testEditJustCreatedTest(){
        //GENERATE USER
        Map <String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userId = responseCreateAuth.jsonPath().getString("id");
        //LOGIN
        Map <String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //EDIT
        String newName = "Changed Name";
        Map <String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        Response responseEditUser = apiCoreRequests.makePutRequestAuth("https://playground.learnqa.ru/api/user/" + userId, editData, header, cookie);

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertJsonByName(responseUserData, "firstName", newName);
   }
    @Test
    @Description("This test checks non-auth user cannot edit his data")
    @DisplayName("Test negative edit user data with non-auth")
    public void testEditUserNotAuthTest(){
        //GENERATE USER
        Map <String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userId = responseCreateAuth.jsonPath().getString("id");
        //LOGIN
        Map <String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        //EDIT
        String newName = "Changed Name";
        Map <String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests.makePutRequestNotAuth("https://playground.learnqa.ru/api/user/" + userId, editData);
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser, "Auth token not supplied");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
    }
    @Test
    @Description("This test checks auth user cannot edit another user data")
    @DisplayName("Test negative edit user data by another user")
    public void testEditUserWithAnotherAuthUserTest(){
        //GENERATE USER
        Map <String, String> userData = DataGenerator.getRegistrationData();
        Response responseCreateUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userId = responseCreateUser.jsonPath().getString("id");

        //LOGIN AS ANOTHER USER
        Map <String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuthAsAnotherUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        this.cookie = this.getCookie(responseGetAuthAsAnotherUser, "auth_sid");
        this.header = this.getHeader(responseGetAuthAsAnotherUser, "x-csrf-token");

        //EDIT
        String newName = "Changed Name";
        Map <String, String> editData = new HashMap<>();
        editData.put("firstName", newName);
        Response responseEditUser = apiCoreRequests.makePutRequestAuth("https://playground.learnqa.ru/api/user/" + userId, editData, header, cookie);
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser, "Please, do not edit test users with ID 1, 2, 3, 4 or 5.");

        //LOGIN AS CREATED USER
        Map <String, String> authDataCreatedUser = new HashMap<>();
        authDataCreatedUser.put("email", userData.get("email"));
        authDataCreatedUser.put("password", userData.get("password"));

        Response responseGetAuthAsCreatedUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authDataCreatedUser);
        this.cookie = this.getCookie(responseGetAuthAsCreatedUser, "auth_sid");
        this.header = this.getHeader(responseGetAuthAsCreatedUser, "x-csrf-token");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
    }
    @Test
    @Description("This test checks ability to edit email with invalid format")
    @DisplayName("Test negative edit email with invalid format")
    public void testEditEmailTest(){
        //GENERATE USER
        Map <String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userId = responseCreateAuth.jsonPath().getString("id");
        //LOGIN
        Map <String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //EDIT
        String invalidEmail = "learnqaexample.com";
        Map <String, String> editData = new HashMap<>();
        editData.put("email", invalidEmail);
        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        Response responseEditUser = apiCoreRequests.makePutRequestAuth("https://playground.learnqa.ru/api/user/" + userId, editData, header, cookie);
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTextEquals(responseEditUser, "Invalid email format");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertJsonByName(responseUserData, "email", userData.get("email"));
    }
    @Test
    @Description("This test checks ability to edit firstName with short value")
    @DisplayName("Test negative edit firstName with short value")
    public void testEditFirstNameTest() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userId = responseCreateAuth.jsonPath().getString("id");
        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //EDIT
        String shortFirstName = "l";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", shortFirstName);
        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        Response responseEditUser = apiCoreRequests.makePutRequestAuth("https://playground.learnqa.ru/api/user/" + userId, editData, header, cookie);
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error", "Too short value for field firstName");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertJsonByName(responseUserData, "firstName", userData.get("firstName"));
    }
}
