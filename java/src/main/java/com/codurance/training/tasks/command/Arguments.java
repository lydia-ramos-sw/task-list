package main.java.com.codurance.training.tasks.command;

import java.util.ArrayList;

interface Arguments{
    default boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            return true;
        }
        System.out.println(validationsErrors.stream().toList());
        return false;
    }

    default boolean validateArgumentsCorrectness(String[] arguments, ArrayList<String> validationsErrors) {
        return true;
    }

    default boolean validateAmountOfArguments(String[] arguments, ArrayList<String> validationsErrors) {
        return true;
    }
}
