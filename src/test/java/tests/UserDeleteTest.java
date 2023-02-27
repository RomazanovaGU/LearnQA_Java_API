package tests;

import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.ApiCoreRequests;
import lib.Assertions;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lib.DataGenerator;
import  org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Delete user cases")
@Feature("Delete user")
public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    String cookie;
    String header;
    @Test
    @TmsLink("CORP-2030")

    @Description("This test checks impossibility to delete user with ID=2")
    @DisplayName("Test negative delete user with id 2")
    public void testTryToDeleteDefaultUserTest(){
        //LOGIN AS USER WITH ID 2
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuthAsAnotherUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);
        this.cookie = this.getCookie(responseGetAuthAsAnotherUser, "auth_sid");
        this.header = this.getHeader(responseGetAuthAsAnotherUser, "x-csrf-token");

        //DELETE
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequestAuth("https://playground.learnqa.ru/api/user/2", header, cookie);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 400);
        Assertions.assertResponseTextEquals(responseDeleteUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5");
    }
    @Test
    @Description("This test checks ability to delete auth user")
    @DisplayName("Test positive delete user")
    public void testDeleteUserTest(){
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

        //DELETE
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequestAuth("https://playground.learnqa.ru/api/user/"+userId, header, cookie);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, header, cookie);
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
        Assertions.assertResponseCodeEquals(responseUserData, 404);
    }
    @Test
    @Description("This test checks impossibility to delete another user")
    @DisplayName("Test negative delete another user")
    public void testDeleteAnotherUserTest(){
        //GENERATE USER FOR DELETING
        Map <String, String> userData = DataGenerator.getRegistrationData();

        Response responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        String userForDeletingId = responseCreateAuth.jsonPath().getString("id");

        //GENERATE NEW USER AS EDITOR
        Map <String, String> userDataOfEditor = DataGenerator.getRegistrationData();
        Response responseCreateEditorUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userDataOfEditor);

        //LOGIN AS EDITOR USER
        Map <String, String> authDataOfEditorUser = new HashMap<>();
        authDataOfEditorUser.put("email", userDataOfEditor.get("email"));
        authDataOfEditorUser.put("password", userDataOfEditor.get("password"));

        Response responseGetAuthAsEditorUser = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authDataOfEditorUser);
        this.cookie = this.getCookie(responseGetAuthAsEditorUser, "auth_sid");
        this.header = this.getHeader(responseGetAuthAsEditorUser, "x-csrf-token");

        //DELETE
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequestAuth("https://playground.learnqa.ru/api/user/"+userForDeletingId, header, cookie);
        Assertions.assertResponseCodeEquals(responseDeleteUser, 200);

        //LOGIN AS USER FOR DELETING
        Map <String, String> authDataUserForDeleting = new HashMap<>();
        authDataUserForDeleting.put("email", userData.get("email"));
        authDataUserForDeleting.put("password", userData.get("password"));

        Response responseGetAuthAsUserForDeleting = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authDataUserForDeleting);
        Assertions.assertResponseCodeEquals(responseGetAuthAsUserForDeleting, 200);
        Assertions.assertJsonByName(responseGetAuthAsUserForDeleting, "user_id",  userForDeletingId);
    }
}

