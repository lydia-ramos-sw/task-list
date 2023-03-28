package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.exceptions.ValidationException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Uncheck extends Check{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        try {
        setArguments(arguments);
        setDone(tasks, arguments[1], false);
        } catch (ValidationException ve) {
            out.println(ve.getMessage());
            Uncheck.help(out);
        }
    }

    public static void help(PrintWriter out) {
        out.println("  uncheck <task ID>");
    }
}
