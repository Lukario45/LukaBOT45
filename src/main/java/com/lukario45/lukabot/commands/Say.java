package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Say extends Command {
    Config c;
    public Say(){
        super("Say","Bot Repeats Arguments", "Say message");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }
//change
    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (c.getAdmins().contains(e.getUser().getNick())) {
            StringBuilder sb = new StringBuilder();
            String[] arguments = e.getMessage().split(" ");
            for (int i = 1; i < arguments.length; i++) {
                sb.append(arguments[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            e.getChannel().send().message(allArgs);
            return true;
        } else {
            e.respond(getHelp());
            return false;
        }
    }
}
