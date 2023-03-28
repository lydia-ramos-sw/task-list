package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.exceptions.ValidationException;

public abstract class CommandWithArguments extends Command{
    public abstract void setArguments(String[] arguments) throws Exception;

    public abstract void validateArgumentsCorrectness(String[] arguments) throws ValidationException;

    public abstract void validateAmountOfArguments(String[] arguments) throws ValidationException;
}
