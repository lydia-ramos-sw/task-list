package test.java.com.codurance.training.tasks;

import main.java.com.codurance.training.tasks.TaskList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public final class ApplicationTest {
    public static final String PROMPT = "> ";
    private final PipedOutputStream inStream = new PipedOutputStream();
    private final PrintWriter inWriter = new PrintWriter(inStream, true);

    private final PipedInputStream outStream = new PipedInputStream();
    private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

    private Thread applicationThread;

    public ApplicationTest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
        PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);
        TaskList taskList = new TaskList(in, out);
        applicationThread = new Thread(taskList);
    }

    @Before public void
    start_the_application() {
        applicationThread.start();
    }

    @After public void
    kill_the_application() throws IOException, InterruptedException {
        if (!stillRunning()) {
            return;
        }

        Thread.sleep(1000);
        if (!stillRunning()) {
            return;
        }

        applicationThread.interrupt();
        throw new IllegalStateException("The application is still running.");
    }

    @Test(timeout = 10000000) public void
    it_works() throws IOException {
        execute("show");

        execute("add project secrets");
        execute("add task secrets id1 Eat more donuts.");
        execute("add task secrets id2 Destroy all humans.");

        execute("show");
        readLines(
            "secrets",
            "    [ ] id1: Eat more donuts.",
            "    [ ] id2: Destroy all humans.",
            ""
        );

        execute("add project training");
        execute("add task training id3 Four Elements of Simple Design");
        execute("add task training id4 SOLID");
        execute("add task training id5 Coupling and Cohesion");
        execute("add task training id6 Primitive Obsession");
        execute("add task training id7 Outside-In TDD");
        execute("add task training id8 Interaction-Driven Design");

        execute("check id1");
        execute("check id3");
        execute("check id5");
        execute("check id6");

        execute("show");
        readLines(
                "secrets",
                "    [x] id1: Eat more donuts.",
                "    [ ] id2: Destroy all humans.",
                "",
                "training",
                "    [x] id3: Four Elements of Simple Design",
                "    [ ] id4: SOLID",
                "    [x] id5: Coupling and Cohesion",
                "    [x] id6: Primitive Obsession",
                "    [ ] id7: Outside-In TDD",
                "    [ ] id8: Interaction-Driven Design",
                ""
        );

        execute("deadline id2 28/03/2023");
        execute("deadline id4 28/03/2023");
        execute("deadline id6 26/01/2023");
        execute("today");
        readLines(
                "secrets",
                "    [ ] id2: Destroy all humans.",
                "",
                "training",
                "    [ ] id4: SOLID",
                ""
        );
        execute("add task training id& wrongTaskOnPurpose");
        readLines("Could not create the task because the id contains special characters");
        readLines("  add project <project name>");
        readLines("  add task <task ID> <project name> <task description>");
        execute("delete id2");
        execute("show");
        readLines(
                "secrets",
                "    [x] id1: Eat more donuts.",
                "",
                "training",
                "    [x] id3: Four Elements of Simple Design",
                "    [ ] id4: SOLID",
                "    [x] id5: Coupling and Cohesion",
                "    [x] id6: Primitive Obsession",
                "    [ ] id7: Outside-In TDD",
                "    [ ] id8: Interaction-Driven Design",
                ""
        );
        execute("quit");
    }

    private void execute(String command) throws IOException {
        read(PROMPT);
        write(command);
    }

    private void read(String expectedOutput) throws IOException {
        int length = expectedOutput.length();
        char[] buffer = new char[length];
        outReader.read(buffer, 0, length);
        assertThat(String.valueOf(buffer), is(expectedOutput));
    }

    private void readLines(String... expectedOutput) throws IOException {
        for (String line : expectedOutput) {
            read(line + lineSeparator());
        }
    }

    private void write(String input) {
        inWriter.println(input);
    }

    private boolean stillRunning() {
        return applicationThread != null && applicationThread.isAlive();
    }
}
