package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsValidator{
    public static boolean validateArgumentsAmount(String[] arguments, ArrayList<String> validationsErrors, int n) {
        if (arguments.length != n) {
            validationsErrors.add("Arguments should be " + n + "and there are only " + arguments.length);
            return false;
        }
        return true;
    }
    public static boolean validateArgumentsAmountOrMore(String[] arguments, ArrayList<String> validationsErrors, int n) {
        if (arguments.length < n) {
            validationsErrors.add("Arguments should be " + n + "and there are only " + arguments.length);
            return false;
        }
        return true;
    }

    public static boolean validateItemToAdd(String argument, ArrayList<String> validationsErrors, String itemLabel) {
        if (argument != null &&
                (argument.equalsIgnoreCase("project")
                        || argument.equalsIgnoreCase("task"))) {
            return true;
        }
        validationsErrors.add(itemLabel + " should have as a value 'project or task' and its value is " + argument);
        return false;
    }

    public static boolean validateString(String argument, ArrayList<String> validationsErrors, String itemLabel) {
        if (argument != null &&
                !argument.isEmpty()) {
            return true;
        }
        validationsErrors.add(itemLabel + " is null or empty");
        return false;
    }

    public static boolean validateDate(String argument, ArrayList<String> validationsErrors, String itemLabel) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(argument);
        } catch (ParseException e) {
            validationsErrors.add(argument + " with cannot be used as " + itemLabel);
            return false;
        }
        return true;
    }

    public static boolean validateTaskIdWithoutSpecialCharacters(String argument, ArrayList<String> validationsErrors) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(argument);
        boolean specialCharacters = m.find();
        if (specialCharacters) {
            validationsErrors.add("Could not create the task because the id contains special characters");
            return false;
        } else {
            return true;
        }
    }

    public static List<Task> validateProjectExists(Map<String, List<Task>> tasks, String argument,
                                                   String itemLabel) {
        List<Task> projectTasks = tasks.get(argument);
        if (projectTasks == null) {
            System.out.println("Could not find a " + itemLabel + " with the name " + argument + ". Please create the project before using it.");
        }
        return projectTasks;
    }

    public static Optional<Task> validateTaskExists(Map<String, List<Task>> tasks, String argument,
                                                    String itemLabel) {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(argument);
        Optional<Task> task = TaskUtils.findTask(tasks, sameId);
        if (!task.isPresent()) {
            System.out.println("Could not find a " + itemLabel + " with the id " + argument + ".");
        }
        return task;
    }
}
