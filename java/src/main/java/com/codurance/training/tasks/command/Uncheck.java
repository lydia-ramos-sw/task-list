package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.List;
import java.util.Map;

public class Uncheck extends Check {
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        setDone(tasks, arguments[1], false);
    }
}
