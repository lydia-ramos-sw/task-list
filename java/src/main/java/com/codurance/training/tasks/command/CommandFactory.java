package main.java.com.codurance.training.tasks.command;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CommandFactory implements Supplier<Command>{
    ADD("add", Add::new),
    CHECK("check", Check::new),
    UNCHECK("uncheck", Uncheck::new),
    DEADLINE("deadline", Deadline::new),
    TODAY("today", Today::new),
    HELP("help", Help::new),
    ERROR("error",ErrorCommand::new),
    SHOW("show", Show::new);

    private static final CommandFactory[] VALUES = values();
    private final String name;
    private final Supplier<? extends Command> command;


    CommandFactory(String name, Supplier<? extends Command> command) {
        this.name = name;
        this.command = command;
    }

    public static CommandFactory fromName(final String name) {
        return Arrays.stream(VALUES)
                .filter(v -> v.name.equals(name))
                .findFirst()
                .orElse(CommandFactory.ERROR);
    }

    @Override
    public Command get() {
        return command.get();
    }
}