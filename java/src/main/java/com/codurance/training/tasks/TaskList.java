package main.java.com.codurance.training.tasks;

import main.java.com.codurance.training.tasks.command.Command;
import main.java.com.codurance.training.tasks.command.CommandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    final Map<String, List<Task>> tasks = new LinkedHashMap<>();
    private final BufferedReader in;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in).run();
    }

    public TaskList(BufferedReader reader) {
        this.in = reader;
    }

    public void run() {
        while (true) {
            TaskUtils.printFlush();
            String command;
            try {
                command = in.readLine();
                if (command.equals(QUIT)) {
                    break;
                }
                processCommand(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processCommand(String commandLine) {
        String[] arguments = commandLine.split(" ");
        Command c = CommandFactory.fromName(arguments[0]).get();
        c.execute(arguments, tasks);
    }

}
