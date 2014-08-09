package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Shorten extends Command {
     Config config;

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {

        if (args.length == 1) {
            String longurl = args[0];
            e.respond(Utils.shortenUrl(args[0]));
            return true;
        } else {
            e.getUser().send().notice( "Improper usage! correct usage: $shorten http://google.com/");
            return false;
        }

    }
}
