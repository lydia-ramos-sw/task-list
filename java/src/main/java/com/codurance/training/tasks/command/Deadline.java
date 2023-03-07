package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Deadline extends Command implements Arguments{

    String taskId;
    String deadline;
    Date deadlineDate;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            deadline(tasks, arguments[2]);
        }
    }

    void deadline(Map<String, List<Task>> tasks, String sDateDeadline) {
        Optional<Task> task = ArgumentsValidator.validateTaskExists(tasks, "task", taskId);
        if (task.isPresent()) {
            task.get().setDeadlineDate(deadlineDate);
        }
    }

    @Override
    public boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            taskId = arguments[1];
            deadline = arguments[2];
            try {
                deadlineDate = new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
            } catch (ParseException e) {
            }
        }
        System.out.println(validationsErrors.stream().toList());
        Deadline.help();
        return false;
    }

    @Override
    public boolean validateArgumentsCorrectness(String[] arguments, ArrayList<String> validationsErrors) {
        boolean argumentsCorrectness = true;
        argumentsCorrectness = ArgumentsValidator.validateString(arguments[1], validationsErrors, "taskId");
        argumentsCorrectness = argumentsCorrectness && ArgumentsValidator.validateDate(arguments[2], validationsErrors, "deadline");
        return argumentsCorrectness;
    }

    @Override
    public boolean validateAmountOfArguments(String[] arguments, ArrayList<String> validationsErrors) {
        return ArgumentsValidator.validateArgumentsAmount(arguments, validationsErrors, 1);
    }

    public static void help() {
        System.out.println("  deadline <task ID> <dd/MM/yyyy>");
    }
}