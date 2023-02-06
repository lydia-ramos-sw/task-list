package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Add extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        if (arguments[1].equals("project")) {
            addProject(tasks, arguments[2]);
        } else if (arguments[1].equals("task")) {
            addTask(tasks, arguments[2], arguments[3], arguments[4]);
        }
    }

    private void addProject(Map<String, List<Task>> tasks, String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(Map<String, List<Task>> tasks, String project, String id, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            System.out.printf("Could not find a project with the name \"%s\".", project);
            System.out.println();
            return;
        }
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(id);
        boolean idContainsSpecialCharacters = m.find();
        if (idContainsSpecialCharacters) {
            System.out.println("Could not create the task because the id contains special characters");
        }
        projectTasks.add(new Task(id, description, false));
    }
}
