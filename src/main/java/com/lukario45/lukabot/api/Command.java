package com.lukario45.lukabot.api;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by zack6849 on 8/8/14.
 */
public abstract class Command {
    private final String name;
    private final String description;
    private final String help;


    protected Command() {
        this.name = "Undefined";
        this.description = "Undefined";
        this.help = "Undefined";
    }

    protected Command(String name) {
        this.name = name;
        this.description = "Undefined";
        this.help = "Undefined";
    }

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.help = "Undefined";
    }

    /**
     * Creates a new Command object
     *
     * @param name        the name of the command
     * @param description a description of the command and what it does
     * @param help        information on how to use the command
     */
    protected Command(String name, String description, String help) {
        this.name = name;
        this.description = description;
        this.help = help;
    }

    /**
     * Set the configuration for the command
     *
     * @param config the config object to pass
     */
    public abstract void setConfig(Config config);

    /**
     * Method to be executed when the command is run
     *
     * @param event the MessageEvent that triggered the command
     * @return true upon success, false upon failure
     */
    public abstract boolean execute(MessageEvent event, String[] args, boolean isPublic);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        return help;
    }
}
