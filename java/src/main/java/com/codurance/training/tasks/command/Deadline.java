package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Deadline extends CommandWithArguments{

    String taskId;
    String deadline;
    Date deadlineDate;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        try {
            setArguments(arguments);
            deadline(tasks, arguments[2]);
        } catch (ValidationException ve) {
            out.println(ve.getMessage());
            Deadline.help(out);
        }
    }

    void deadline(Map<String, List<Task>> tasks, String sDateDeadline) throws ValidationException {
        Optional<Task> task = ArgumentsValidator.validateTaskExists(tasks, taskId, "task");
        if (task.isPresent()) {
            task.get().setDeadlineDate(deadlineDate);
        }
    }

    @Override
    public void setArguments(String[] arguments) throws ValidationException {
        validateAmountOfArguments(arguments);
        validateArgumentsCorrectness(arguments);
        taskId = arguments[1];
        deadline = arguments[2];
        try {
            deadlineDate = new SimpleDateFormat("dd/MM/yyyy").parse(deadline);
        } catch (ParseException e) {
        }
    }

    @Override
    public void validateArgumentsCorrectness(String[] arguments) throws ValidationException {
        ArgumentsValidator.validateString(arguments[1], "taskId");
        ArgumentsValidator.validateDate(arguments[2], "deadline");
    }

    @Override
    public void validateAmountOfArguments(String[] arguments) throws ValidationException {
        ArgumentsValidator.validateArgumentsAmount(arguments, 3);
    }

    public static void help(PrintWriter out) {
        out.println("  deadline <task ID> <dd/MM/yyyy>");
    }
}