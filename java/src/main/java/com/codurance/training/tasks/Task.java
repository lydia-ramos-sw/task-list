package main.java.com.codurance.training.tasks;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
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

    public void setDeadlineDate(String sDeadlineDate) throws ParseException {
        this.deadlineDate =new SimpleDateFormat("dd/MM/yyyy").parse(sDeadlineDate);
    }

    public void print() {
        System.out.printf("    [%c] %s: %s%n", (this.isDone() ? 'x' : ' '), this.getId(), this.getDescription());
    }
}
