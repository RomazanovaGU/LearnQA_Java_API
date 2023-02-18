import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentCheck {

    @ParameterizedTest
    @ValueSource(strings = {"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30", "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0" })
    public void userAgentCheck(String condition){
        JsonPath response = RestAssured
                .given()
                .header("User-Agent", condition)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();
        String user_agent = response.getString("user_agent");
        String platform = response.getString("platform");
        String browser = response.getString("browser");
        String device = response.getString("device");
        if (condition.equals("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"))
        {
            assertEquals ("Mobile",  platform, "user_agent: " + user_agent + " has unexpected platform value: " + platform);
            assertEquals ("No",  browser, "user_agent: " + user_agent + " has unexpected browser value: " + browser);
            assertEquals ("Android",  device, "user_agent: " + user_agent + " has unexpected device value: " + device);
        }
        else if (condition.equals("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1"))
        {
            assertEquals ("Mobile",  platform, "user_agent: " + user_agent + " has unexpected platform value: " + platform);
            assertEquals ("Chrome",  browser, "user_agent: " + user_agent + " has unexpected browser value: " + browser);
            assertEquals ("iOS",  device, "user_agent: " + user_agent + " has unexpected device value: " + device);
        }
        else if (condition.equals("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"))
        {
            assertEquals ("Googlebot",  platform, "user_agent: " + user_agent + " has unexpected platform value: " + platform);
            assertEquals ("Unknown",  browser, "user_agent: " + user_agent + " has unexpected browser value: " + browser);
            assertEquals ("Unknown",  device, "user_agent: " + user_agent + " has unexpected device value: " + device);
        }
        else if (condition.equals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0"))
        {
            assertEquals ("Web",  platform, "user_agent: " + user_agent + " has unexpected platform value: " + platform);
            assertEquals ("Chrome",  browser, "user_agent: " + user_agent + " has unexpected browser value: " + browser);
            assertEquals ("No",  device, "user_agent: " + user_agent + " has unexpected device value: " + device);
        }
        else if (condition.equals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0"))
        {
            assertEquals ("Mobile",  platform, "user_agent: " + user_agent + " has unexpected platform value: " + platform);
            assertEquals ("No",  browser, "user_agent: " + user_agent + " has unexpected browser value: " + browser);
            assertEquals ("iPhone",  device, "user_agent: " + user_agent + " has unexpected device value: " + device);
        }
        else {
            throw new IllegalArgumentException("Condition value is unknown: " + condition);
        }
    }
}
