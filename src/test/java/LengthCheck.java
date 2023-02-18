import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LengthCheck {
    String var = "Check that the string length is more than 15 symbols";
    @Test
    public void lengthCheck() {

        assertTrue( 15 < var.length(), "The length of given variable is less than 15 symbols");    }
}
