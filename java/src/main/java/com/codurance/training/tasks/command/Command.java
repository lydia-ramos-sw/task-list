package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public abstract class Command{
    PrintWriter out;

    public abstract void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out);
}
