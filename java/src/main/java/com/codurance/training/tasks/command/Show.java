package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Show extends Command implements Arguments{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            TaskUtils.show(tasks, this.out);
        }
    }

    public static void help(PrintWriter out) {
        out.println("  show");
    }
}
