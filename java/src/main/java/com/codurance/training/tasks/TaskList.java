package main.java.com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final Map<String, List<Task>> tasks = new LinkedHashMap<>();
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
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            try {
                execute(command);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(subcommandRest[1]);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 3);
            addTask(projectTask[0], projectTask[1], projectTask[2]);
        }
    }

    private void addProject(String name) {
        tasks.put(name, new ArrayList<>());
    }

    private void addTask(String project, String id, String description) {
        List<Task> projectTasks = tasks.get(project);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", project);
            out.println();
            return;
        }
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(id);
        boolean idContainsSpecialCharacters = m.find();
        if (idContainsSpecialCharacters) {
            out.println("Could not create the task because the id contains special characters");
        }
        projectTasks.add(new Task(id, description, false));
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
