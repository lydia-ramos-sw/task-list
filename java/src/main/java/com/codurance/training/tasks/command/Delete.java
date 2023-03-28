package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Delete extends CommandWithArguments{

    String taskId;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        try{
        setArguments(arguments);
        removeTask(tasks);
        } catch (ValidationException ve){
            out.println(ve.getMessage());
            Delete.help(out);
        }
    }

    void removeTask(Map<String, List<Task>> tasks) throws ValidationException {
        ArgumentsValidator.validateTaskExists(tasks, taskId, "task" );
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(taskId);
        TaskUtils.removeTask(tasks, sameId);
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

    public static void help(PrintWriter out) {
        out.println("  delete <task ID>");
    }
}
