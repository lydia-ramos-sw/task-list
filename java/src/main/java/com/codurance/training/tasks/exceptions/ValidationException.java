package main.java.com.codurance.training.tasks.exceptions;

public class ValidationException extends Exception {
    public ValidationException(String errorMessage)  {
        super(errorMessage);
    }
}
