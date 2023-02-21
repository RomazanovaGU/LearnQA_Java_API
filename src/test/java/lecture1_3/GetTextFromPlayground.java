package lecture1_3;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetTextFromPlayground {
    @Test
//    public void testGetTextFromPlayground(){
//        Response response = RestAssured.
//                get("https://playground.learnqa.ru/api/get_text")
//                .andReturn();
//        response.prettyPrint();
//    }
    public void testGetJsonFromPlayground1(){
        JsonPath response = RestAssured.
               get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        ArrayList<Map<String,String>> messages = response.get("messages");
        Map <String,String> secondMessage = messages.get(1);
        System.out.println(secondMessage.get("message"));
    };
}



