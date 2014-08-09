package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Nope extends Command {
    Config c;
    public Nope(){
        super("Nope","Nope","Nope");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        e.getChannel().send().message("nope: http://www.youtube.com/watch?v=gvdf5n-zI14)");
        return true;
    }
}
