package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Add extends Command implements Arguments{
    String itemToAdd;
    String project;
    String taskId;
    String task;


    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            if (itemToAdd.equals("project")) {
                addProject(tasks, project);
            } else {
                addTask(tasks, project, taskId, task);
            }
        }
    }

    @Override
    public boolean setArguments(String[] arguments) {
        ArrayList<String> validationsErrors = new ArrayList<>();
        if (validateAmountOfArguments(arguments, validationsErrors) & validateArgumentsCorrectness(arguments, validationsErrors)) {
            itemToAdd = arguments[1];
            project = arguments[2];
            if (arguments[1].equals("task")) {
                taskId = arguments[3];
                task = "";
                for (int i=4; i<arguments.length; i++){
                    task += arguments[i]+" ";
                }
                task = task.trim();
            }
            return true;
        }
        this.out.println(validationsErrors.stream().toList());
        Add.help(this.out);
        return false;
    }

    public boolean validateArgumentsCorrectness(String[] arguments, ArrayList<String> validationsErrors) {
        boolean argumentsCorrectness = true;
        argumentsCorrectness = ArgumentsValidator.validateItemToAdd(arguments[1], validationsErrors, "itemToAdd");
        argumentsCorrectness = argumentsCorrectness && ArgumentsValidator.validateString(arguments[2], validationsErrors, "project");
        if (arguments[1].equals("task")) {
            argumentsCorrectness = argumentsCorrectness && ArgumentsValidator.validateTaskIdWithoutSpecialCharacters(arguments[3], validationsErrors);
            argumentsCorrectness = argumentsCorrectness && ArgumentsValidator.validateString(arguments[4], validationsErrors, "task");
        }
        return argumentsCorrectness;
    }

    public boolean validateAmountOfArguments(String[] arguments, ArrayList<String> validationsErrors) {
        boolean correctAmountOfArguments = true;
        if (arguments[1].equals("project")) {
            correctAmountOfArguments = ArgumentsValidator.validateArgumentsAmount(arguments, validationsErrors, 3);
        } else if (arguments[1].equals("task")) {
            correctAmountOfArguments = ArgumentsValidator.validateArgumentsAmountOrMore(arguments, validationsErrors, 5);
        } else {
            correctAmountOfArguments = false;
        }
        return correctAmountOfArguments;
    }

    public static void help(PrintWriter out) {
        out.println("  add project <project name>");
        out.println("  add task <task ID> <project name> <task description>");
    }

    private void addProject(Map<String, List<Task>> tasks, String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(Map<String, List<Task>> tasks, String project, String id, String description) {
        List<Task> projectTasks = ArgumentsValidator.validateProjectExists(tasks, project, "project");
        if (projectTasks != null) {
            tasks.get(project).add(new Task(id, description, false));
        }
    }
}
