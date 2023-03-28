package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Add extends CommandWithArguments{
    String itemToAdd;
    String project;
    String taskId;
    String task;

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        try {
            setArguments(arguments);
            if (itemToAdd.equals("project")) {
                addProject(tasks, project);
            } else {
                addTask(tasks, project, taskId, task);
            }
        } catch (ValidationException ve){
            out.println(ve.getMessage());
            Add.help(out);
        }
    }

    @Override
    public void setArguments(String[] arguments) throws ValidationException {
        validateAmountOfArguments(arguments);
        validateArgumentsCorrectness(arguments);
        itemToAdd = arguments[1];
        project = arguments[2];
        if (arguments[1].equals("task")) {
            taskId = arguments[3];
            task = "";
            for (int i = 4; i < arguments.length; i++) {
                task += arguments[i] + " ";
            }
            task = task.trim();
        }

    }

    public void validateArgumentsCorrectness(String[] arguments) throws ValidationException {
        ArgumentsValidator.validateItemToAdd(arguments[1], "itemToAdd");
        ArgumentsValidator.validateString(arguments[2], "project");
        if (arguments[1].equals("task")) {
            ArgumentsValidator.validateTaskIdWithoutSpecialCharacters(arguments[3]);
            ArgumentsValidator.validateString(arguments[4], "task");
        }
    }

    public void validateAmountOfArguments(String[] arguments) throws ValidationException {
        if (arguments[1].equals("project")) {
            ArgumentsValidator.validateArgumentsAmount(arguments, 3);
        } else if (arguments[1].equals("task")) {
            ArgumentsValidator.validateArgumentsAmountOrMore(arguments, 5);
        } else {
            new ValidationException("Bad arguments");
        }
    }

    public static void help(PrintWriter out) {
        out.println("  add project <project name>");
        out.println("  add task <task ID> <project name> <task description>");
    }

    private void addProject(Map<String, List<Task>> tasks, String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(Map<String, List<Task>> tasks, String project, String id, String description) throws ValidationException {
        ArgumentsValidator.validateProjectExists(tasks, project, "project");
        ArgumentsValidator.validateTaskDoesNotExist(tasks, taskId, "task" );
        tasks.get(project).add(new Task(id, description, false));
    }
}
