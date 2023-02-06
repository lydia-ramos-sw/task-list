package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Today extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        Predicate<Task> deadlineToday = t -> t.getDeadlineDate()!=null && TaskUtils.isToday(t.getDeadlineDate());
        Map<String, List<Task>> tasksDeadlineToday = TaskUtils.findTasks(tasks, deadlineToday);
        if (tasksDeadlineToday.isEmpty()){
            System.out.println("No tasks with deadline today");
            return;
        }
        TaskUtils.show(tasksDeadlineToday);
    }
}
