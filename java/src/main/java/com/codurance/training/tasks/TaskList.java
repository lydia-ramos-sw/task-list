package main.java.com.codurance.training.tasks;

import main.java.com.codurance.training.tasks.command.Command;
import main.java.com.codurance.training.tasks.command.CommandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;
    private final PrintWriter out;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            printFlush(out);
            String command;
            try {
                command = in.readLine();
                processCommand(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
        }
    }

    private void processCommand(String commandLine) {
        String[] commandRest = commandLine.split(" ");
        Command c = CommandFactory.fromName(commandRest[0]).get();
        c.execute(commandRest);
    }

    private void printFlush(PrintWriter out){
        out.print("> ");
        out.flush();
    }

    private void execute(String commandLine) throws ParseException {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show" -> show(tasks);
            case "add" -> add(commandRest[1]);
            case "check" -> check(commandRest[1]);
            case "uncheck" -> uncheck(commandRest[1]);
            case "deadline" -> deadline(commandRest[1]);
            case "today" -> today();
            case "help" -> help();
            default -> error(command);
        }
    }

    private void show(Map<String, List<Task>> tasks) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                task.print(out);
            }
            out.println();
        }
    }

    private void add(String commandLine) {

    }



    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(idString);
        Optional<Task> task = findTask(tasks, sameId);
        if(task.isPresent()) {
            task.get().setDone(done);
        } else {
            taskCouldNotBeFound(idString);
        }
    }

    private void deadline(String paramsLine) throws ParseException {
        String[] paramsString = paramsLine.split(" ", 2);
        Predicate<Task> sameId = t -> t.getId().equalsIgnoreCase(paramsString[0]);
        String sDateDeadline = paramsString[1];
        Optional<Task> task = findTask(tasks, sameId);
        if(task.isPresent()) {
            task.get().setDeadlineDate(sDateDeadline);
        } else {
            taskCouldNotBeFound(paramsString[0]);
        }
    }

    private void today() {
        Predicate<Task> deadlineToday = t -> t.getDeadlineDate()!=null && isToday(t.getDeadlineDate());
        Map<String, List<Task>> tasksDeadlineToday = findTasks(tasks, deadlineToday);
        if (tasksDeadlineToday.isEmpty()){
            out.println("No tasks with deadline today");
            return;
        }
        show(tasksDeadlineToday);
    }

    public boolean isToday(Date date){
        Calendar today = Calendar.getInstance();
        Calendar specifiedDate  = Calendar.getInstance();
        specifiedDate.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
                &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
                &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
    }
    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <task ID> <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <task ID> <dd/MM/yyyy>");
        out.println("  today");
        out.println("  quit");
        out.println();
    }

    private void error(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private Optional<Task> findTask(Map<String, List<Task>> tasks, Predicate<Task> p){
        Optional<Task> task = Optional.empty();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            task = project.getValue().stream().filter(p).findAny();
            if(task.isPresent()) {
                break;
            }
        }
        return task;
    }

    private Map<String, List<Task>> findTasks(Map<String, List<Task>> tasks, Predicate<Task> p){
        Map<String, List<Task>> filteredTasks =  new LinkedHashMap<>();
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            filteredTasks.put(project.getKey(), project.getValue().stream().filter(p).toList());
        }
        return filteredTasks;
    }

    private void taskCouldNotBeFound(String taskId){
        out.printf("Could not find a task with an ID of %s.", taskId);
        out.println();
    }
}
