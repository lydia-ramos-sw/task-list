package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Today extends Command implements Arguments{

    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks, PrintWriter out) {
        this.out = out;
        boolean argumentSettingWentOk = setArguments(arguments);
        if (argumentSettingWentOk) {
            today(tasks);
        }
    }

    void today(Map<String, List<Task>> tasks) {
        Predicate<Task> deadlineToday = t -> t.getDeadlineDate() != null && TaskUtils.isToday(t.getDeadlineDate());
        Map<String, List<Task>> tasksDeadlineToday = TaskUtils.findTasks(tasks, deadlineToday);
        if (tasksDeadlineToday.isEmpty()) {
            this.out.println("No tasks with deadline today");
            return;
        }
        TaskUtils.show(tasksDeadlineToday, this.out);
    }

    public static void help(PrintWriter out) {
        out.println("  today");
    }
}
