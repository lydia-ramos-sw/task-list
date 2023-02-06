package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.List;
import java.util.Map;

public class Help extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        System.out.println("Commands:");
        System.out.println("  show");
        System.out.println("  add project <project name>");
        System.out.println("  add task <task ID> <project name> <task description>");
        System.out.println("  check <task ID>");
        System.out.println("  uncheck <task ID>");
        System.out.println("  deadline <task ID> <dd/MM/yyyy>");
        System.out.println("  today");
        System.out.println("  quit");
        System.out.println();
    }
}
