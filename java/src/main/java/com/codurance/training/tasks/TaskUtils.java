package main.java.com.codurance.training.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class TaskUtils{

    public static Optional<Task> findTask(Map<String, List<Task>> tasks, Predicate<Task> p){
        Optional<Task> task = Optional.empty();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            task = project.getValue().stream().filter(p).findAny();
            if(task.isPresent()) {
                break;
            }
        }
        return task;
    }

    public static Map<String, List<Task>> findTasks(Map<String, List<Task>> tasks, Predicate<Task> p){
        Map<String, List<Task>> filteredTasks =  new LinkedHashMap<>();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            filteredTasks.put(project.getKey(), project.getValue().stream().filter(p).toList());
        }
        return filteredTasks;
    }

    public static void taskCouldNotBeFound(String taskId){
        System.out.printf("Could not find a task with an ID of %s.", taskId);
        System.out.println();
    }

    public static boolean isToday(Date date){
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate  = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }

    public static void show(Map<String, List<Task>> tasks) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            System.out.println(project.getKey());
            for (Task task : project.getValue()) {
                task.print();
            }
            System.out.println();
        }
    }

    public static void printFlush(){
        System.out.print("> ");
        System.out.flush();
    }
}
