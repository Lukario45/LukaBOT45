package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Eat extends Command {
    Config c;
    public Eat(){
        super("Eat", "Eat Something","eat arg");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (args.length == 1){
            e.getChannel().send().message(e.getUser().getNick() + " has eaten a " + args[0]);
            return true;
        } else{
            e.respond(getHelp());
            return false;//t
        }

    }
}
