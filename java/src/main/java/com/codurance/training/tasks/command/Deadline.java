package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class Deadline extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(arguments[1]);
        String sDateDeadline = arguments[2];
        Optional<Task> task = TaskUtils.findTask(tasks, sameId);
        if(task.isPresent()) {
            try {
                task.get().setDeadlineDate(sDateDeadline);
            } catch (ParseException e) {
                System.out.print("Deadline could not be set to task ".concat(task.get().getId()));
            }
        } else {
            TaskUtils.taskCouldNotBeFound(arguments[0]);
        }
    }
}