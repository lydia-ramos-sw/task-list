package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Uncheck extends Check implements Arguments{
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        setDone(tasks, arguments[1], false);
    }

    @Override
    public boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            taskId = arguments[1];
            return true;
        }
        System.out.println(validationsErrors.stream().toList());
        Uncheck.help();
        return false;
    }

    public static void help() {
        System.out.println("  uncheck <task ID>");
    }
}
