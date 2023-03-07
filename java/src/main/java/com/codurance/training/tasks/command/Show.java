package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.util.List;
import java.util.Map;

public class Show extends Command implements Arguments{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            TaskUtils.show(tasks);
        }
    }

    public static void help() {
        System.out.println("  show");
    }
}
