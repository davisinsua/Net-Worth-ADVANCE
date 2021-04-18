package utility;

public class Util {
    public static boolean assertsEnabledTest() {
        boolean assertsEnabled = false;
        assert assertsEnabled = true;

        if(assertsEnabled) {
            System.out.println("Asserts enabled, continuing");
            return true;
        } else {
            System.err.println("Asserts are not enabled, closing");
            System.exit(1);
            return false;
        }
    }
}
