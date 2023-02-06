package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.List;
import java.util.Map;

public class ErrorCommand extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        System.out.printf("I don't know what the command \"%s\" is.", arguments[0]);
        System.out.println();
    }
}
