package main.java.com.codurance.training.tasks.command;

import main.java.com.codurance.training.tasks.Task;
import main.java.com.codurance.training.tasks.TaskUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class Check extends Command{
    @Override
    public void execute(String[] arguments, Map<String, List<Task>> tasks) {
        setDone(tasks, arguments[1], true);
    }

    void setDone(Map<String, List<Task>> tasks, String idString, boolean done) {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(idString);
        Optional<Task> task = TaskUtils.findTask(tasks, sameId);
        if (task.isPresent()) {
            task.get().setDone(done);
        } else {
            TaskUtils.taskCouldNotBeFound(idString);
        }
    }
}