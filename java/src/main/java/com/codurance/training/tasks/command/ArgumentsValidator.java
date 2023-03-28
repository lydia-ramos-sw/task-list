package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsValidator{
    public static void validateArgumentsAmount(String[] arguments, int n) throws ValidationException {
        if (arguments.length != n) {
            throw new ValidationException("Arguments should be " + n + "and there are " + arguments.length);
        }
    }
    public static void validateArgumentsAmountOrMore(String[] arguments, int n) throws ValidationException {
        if (arguments.length < n) {
            throw new ValidationException("Arguments should be " + n + "and there are only " + arguments.length);
        }
    }

    public static void validateItemToAdd(String argument, String itemLabel) throws ValidationException {
        if (!(argument != null &&
                (argument.equalsIgnoreCase("project")
                        || argument.equalsIgnoreCase("task")))) {
            throw new ValidationException(itemLabel + " should have as a value 'project or task' and its value is " + argument);
        }
    }

    public static void validateString(String argument, String itemLabel) throws ValidationException {
        if (argument == null ||
                argument.isEmpty()) {
            throw new ValidationException(itemLabel + " is null or empty");
        }
    }

    public static void validateDate(String argument, String itemLabel) throws ValidationException {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(argument);
        } catch (ParseException e) {
            throw new ValidationException(argument + " with cannot be used as " + itemLabel);
        }
    }

    public static void validateTaskIdWithoutSpecialCharacters(String argument) throws ValidationException {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(argument);
        boolean specialCharacters = m.find();
        if (specialCharacters) {
            throw new ValidationException("Could not create the task because the id contains special characters");
        }
    }

    public static List<Task> validateProjectExists(Map<String, List<Task>> tasks, String argument,
                                                   String itemLabel) throws ValidationException {
        List<Task> projectTasks = tasks.get(argument);
        if (projectTasks == null) {
            throw new ValidationException("Could not find a " + itemLabel + " with the name " + argument + ". Please create the project before using it.");
        }
        return projectTasks;
    }

    public static Optional<Task> validateTaskExists(Map<String, List<Task>> tasks, String argument,
                                                    String itemLabel) throws ValidationException {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(argument);
        Optional<Task> task = TaskUtils.findTask(tasks, sameId);
        if (!task.isPresent()) {
            throw new ValidationException("Could not find a " + itemLabel + " with the id " + argument + ".");
        }
        return task;
    }

    public static Optional<Task> validateTaskDoesNotExist(Map<String, List<Task>> tasks, String argument,
                                                    String itemLabel) throws ValidationException {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(argument);
        Optional<Task> task = TaskUtils.findTask(tasks, sameId);
        if (task.isPresent()) {
            throw new ValidationException("A " + itemLabel + " already exists, with the id " + argument + ".");
        }
        return task;
    }
}
