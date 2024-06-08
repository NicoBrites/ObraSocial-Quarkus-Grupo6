package quarkus.exception;


    public class IncorrectUsernameOrPasswordException extends RuntimeException {
        public IncorrectUsernameOrPasswordException() {
            super("Username o contrasenia incorrecta");
        }
    }


