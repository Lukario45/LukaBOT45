package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.Bot;
import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Slap extends Command {
    Config c;
    public Slap(){
        super("Slap", "Slap Someone", "slap username");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;

    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (args.length == 1) {
            String t = args[0];
            e.getChannel().send().action( "slaps " + t + " around a bit with a stack trace");
            return true;
        } else {
           e.respond(getHelp());
            return false;
        }
    }
}
