/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Kevin
 */
public abstract class Command {

    public final String name, usage, help;
    public final boolean perms;

    /**
     *
     * @param name
     * @param usage
     * @param help
     */
    public Command(String name, String usage, String help, boolean perms) {
        this.name = name;
        this.usage = usage;
        this.help = help;
        this.perms = perms;
    }

    public String getName() {
        return name;
    }

    public boolean getPerms() {
        return perms;
    }

    public String getUsage() {
        return usage;
    }

    public String getHelp() {
        return help;
    }

    public abstract boolean execute(MessageEvent e);
}
