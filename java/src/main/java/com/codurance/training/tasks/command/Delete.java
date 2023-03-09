package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class Delete extends Command implements Arguments{

    String taskId;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            removeTask(tasks);
        }
    }

    void removeTask(Map<String, List<Task>> tasks) {
        Optional<Task> task = ArgumentsValidator.validateTaskExists(tasks, taskId, "task" );
        if (task.isPresent()) {
            Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(taskId);
            TaskUtils.removeTask(tasks, sameId);
        }
    }

    @Override
    public boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            taskId = arguments[1];
            return true;
        }
        this.out.println(validationsErrors.stream().toList());
        Deadline.help(this.out);
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
        return ArgumentsValidator.validateArgumentsAmount(arguments, validationsErrors, 2);
    }

    public static void help(PrintWriter out) {
        out.println("  delete <task ID>");
    }
}
