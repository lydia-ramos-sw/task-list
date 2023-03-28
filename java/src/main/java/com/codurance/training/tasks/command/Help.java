package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Help extends Command {
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        System.out.println("Commands:");
        Show.help(this.out);
        Add.help(this.out);
        Check.help(this.out);
        Uncheck.help(this.out);
        Deadline.help(this.out);
        Today.help(this.out);
        Delete.help(this.out);
        System.out.println("  quit");
        System.out.println();
    }
}
