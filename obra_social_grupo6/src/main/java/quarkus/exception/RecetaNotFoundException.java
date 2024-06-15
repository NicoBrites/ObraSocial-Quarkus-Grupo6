package quarkus.exception;

public class RecetaNotFoundException extends RuntimeException{
    public RecetaNotFoundException(String message) {
        super(message);
    }
}