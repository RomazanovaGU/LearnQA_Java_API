package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest (String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }
    @Step("Make a GET-request with  auth cookie only")
    public Response makeGetRequestWithCookie (String url, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }
    @Step("Make a GET-request with token only")
    public Response makeGetRequestWithToken (String url, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }
    @Step("Make a POST-request with authData in body")
    public Response makePostRequest (String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }

    @Step("Make a POST-request for user registration with invalid email parameter")
    public Response makePostRequestWithInvalidEmailParam (String url, String invalidEmail){
        Map <String, String> updatedUserData = DataGenerator.getRegistrationData();
            updatedUserData.replace("email", updatedUserData.get("email"), invalidEmail);
            return given()
                    .filter(new AllureRestAssured())
                    .body(updatedUserData)
                    .post(url)
                    .andReturn();
    }

    @Step("Make a POST-request for user registration with short value of name parameter")
    public Response makePostRequestWithShortNameParam (String url, String nameParameter){
        Map <String, String> updatedUserData = DataGenerator.getRegistrationData();
        String shortValue = "l";
        if (updatedUserData.containsKey(nameParameter)){
            updatedUserData.replace(nameParameter, updatedUserData.get(nameParameter), shortValue);
            return given()
                    .filter(new AllureRestAssured())
                    .body(updatedUserData)
                    .post(url)
                    .andReturn();
        }
        return null;
    }
    @Step("Make a POST-request for user registration with long value of name parameter")
    public Response makePostRequestWithLongNameParam (String url, String nameParameter){
        Map <String, String> updatedUserData = DataGenerator.getRegistrationData();
        String shortValue = "learnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqalearnqa";
        if (updatedUserData.containsKey(nameParameter)){
            updatedUserData.replace(nameParameter, updatedUserData.get(nameParameter), shortValue);
            return given()
                    .filter(new AllureRestAssured())
                    .body(updatedUserData)
                    .post(url)
                    .andReturn();
        }
        return null;
    }
    @Step("Make a POST-request for user registration with one missed parameter")
    public Response makePostRequestWithMissedParam (String url, String missedParam){
        Map <String, String> updatedUserData = DataGenerator.getRegistrationData();
        if (updatedUserData.containsKey(missedParam)){
            updatedUserData.remove(missedParam);
            return given()
                    .filter(new AllureRestAssured())
                    .body(updatedUserData)
                    .post(url)
                    .andReturn();
        }
        return null;
    }
    @Step("Make a PUT-request with token and auth cookie")
    public Response makePutRequestAuth (String url, Map<String, String> userData, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .body(userData)
                .put(url)
                .andReturn();
    }
    @Step("Make a PUT-request without token and auth cookie")
    public Response makePutRequestNotAuth (String url, Map<String, String> userData){
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .put(url)
                .andReturn();
    }
}
