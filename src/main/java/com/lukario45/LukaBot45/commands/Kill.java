/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukario45.LukaBot45.commands;

import test.Command;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Kevin
 */
public class Kill extends Command {

    public Kill() {

        super("Kill", "$kill", "$kill: Kills the bot from the IRC server.", true);
    }
    private boolean opPerms = true;
    private String usage = "Usage: $kill";
    private String help = "$kill: Used to Disconnect the Bot";

    public boolean getOpPerms() {
        return this.opPerms;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getHelp() {
        return this.help;
    }

    @Override
    public boolean execute(MessageEvent e) {
        String args[] = e.getMessage().split(" ");
        for (Channel ch : e.getBot().getChannels()) {
            e.getBot().partChannel(ch, "Killed by " + e.getUser().getNick() + " in " + e.getChannel().getName());
        }
        e.getBot().disconnect();
        e.getBot().shutdown();
        return true;
    }
}
