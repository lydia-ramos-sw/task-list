package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;

import java.util.List;
import java.util.Map;

public class Help extends Command implements Arguments{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            System.out.println("Commands:");
            Show.help();
            Add.help();
            Check.help();
            Uncheck.help();
            Deadline.help();
            Today.help();
            Delete.help();
            System.out.println("  quit");
            System.out.println();
        }
    }

    public static void help() {
        System.out.println("  show");
    }
}
