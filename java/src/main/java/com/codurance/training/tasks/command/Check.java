package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Check extends CommandWithArguments{

    String taskId;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        try {
            setArguments(arguments);
            setDone(tasks, arguments[1], true);
        } catch (ValidationException ve) {
            out.println(ve.getMessage());
            Check.help(out);
        }
    }

    @Override
    public void setArguments(String[] arguments) throws ValidationException {
        validateAmountOfArguments(arguments);
        validateArgumentsCorrectness(arguments);
        taskId = arguments[1];
    }

    @Override
    public void validateArgumentsCorrectness(String[] arguments) throws ValidationException {
        ArgumentsValidator.validateString(arguments[1], "taskId");
    }

    @Override
    public void validateAmountOfArguments(String[] arguments) throws ValidationException {
        ArgumentsValidator.validateArgumentsAmount(arguments, 2);
    }

    void setDone(Map<String, List<Task>> tasks, String idString, boolean done) throws ValidationException {
        Optional<Task> task = ArgumentsValidator.validateTaskExists(tasks, idString, "task");
        if (task.isPresent()) {
            task.get().setDone(done);
        }
    }

    public static void help(PrintWriter out) {
        out.println("  check <task ID>");
    }
}