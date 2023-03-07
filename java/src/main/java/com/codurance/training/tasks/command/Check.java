package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Check extends Command implements Arguments{

    String taskId;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            setDone(tasks, arguments[1], true);
        }
    }

    @Override
    public boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            taskId = arguments[1];
            return true;
        }
        System.out.println(validationsErrors.stream().toList());
        Check.help();
        return false;
    }

    @Override
    public boolean validateArgumentsCorrectness(String[] arguments, ArrayList<String> validationsErrors) {
        boolean argumentsCorrectness = true;
        argumentsCorrectness = ArgumentsValidator.validateString(arguments[1], validationsErrors, "taskId");
        return argumentsCorrectness;
    }

    @Override
    public boolean validateAmountOfArguments(String[] arguments, ArrayList<String> validationsErrors) {
        return ArgumentsValidator.validateArgumentsAmount(arguments, validationsErrors, 1);
    }

    void setDone(Map<String, List<Task>> tasks, String idString, boolean done) {
        Optional<Task> task = ArgumentsValidator.validateTaskExists(tasks, idString, idString);
        if (task.isPresent()) {
            task.get().setDone(done);
        }
    }

    public static void help() {
        System.out.println("  check <task ID>");
    }
}