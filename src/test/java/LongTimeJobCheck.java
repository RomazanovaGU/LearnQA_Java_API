import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LongTimeJobCheck {
    @Test
    public void requestToLongTimeJob() throws InterruptedException {
        String token;
        int delay;
        Map <String, String> params = new HashMap<>();
        JsonPath response = RestAssured.
                get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        token = response.get("token");
        params.put("token", token);
        delay = response.get("seconds");
        System.out.println("token: " + token + " time for job execution: " + delay);
        System.out.println("========================================");
        JsonPath secondResponse = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = secondResponse.get("status");
        if (!status.equals("Job is NOT ready")) {
            System.out.println("Status has unexpected value: " + status);
        }
        else {
            System.out.println("status: " + status);
        }
        System.out.println("========================================");
        TimeUnit.SECONDS.sleep(delay);
        JsonPath thirdResponse = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        status = thirdResponse.get("status");
        if (!status.equals("Job is ready")) {
            System.out.println("Status has unexpected value: " + status);
        }
        else {
            System.out.println("status: " + status);
        }
        String result = thirdResponse.get("result");
        if (result.equals(null)) {
            System.out.println("Result has unexpected value: " + result);
        }
        else {
            System.out.println("result: " + result);
        }
    };
};