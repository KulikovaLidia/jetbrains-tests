package exceptions;

public class TestException extends Exception {
    public TestException(String errorMessage) {
        super("Test creation error: " + errorMessage);
    }

}
