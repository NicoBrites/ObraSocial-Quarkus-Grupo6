package quarkus.exception;

public class UsernameAlreadyExistsException  extends RuntimeException{
    public UsernameAlreadyExistsException() {
        super("Usuario ya esta registrado");
    }
}
