import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class FindAllRedirects {
    @Test
    public void findAllRedirectSequence() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        String url = response.getHeader("Location");
        int statusCode = response.getStatusCode();
        while (statusCode != 200) {
            Response nextResponse = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();
            System.out.println("Redirect to : " + url);
            statusCode = nextResponse.getStatusCode();
            url = nextResponse.getHeader("Location");
        };
    };
};
