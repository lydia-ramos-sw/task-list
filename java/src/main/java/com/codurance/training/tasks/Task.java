package main.java.com.codurance.training.tasks;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Task {
    private final String id;
    private final String description;
    private boolean done;
    private Date deadlineDate;

    public Task(String id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String sDeadlineDate) throws ParseException {
        this.deadlineDate =new SimpleDateFormat("dd/MM/yyyy").parse(sDeadlineDate);
    }

    public void print(PrintWriter writer) {
        writer.printf("    [%c] %s: %s%n", (this.isDone() ? 'x' : ' '), this.getId(), this.getDescription());
    }
}
