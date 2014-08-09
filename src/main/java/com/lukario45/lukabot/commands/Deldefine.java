package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.DefineYML;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Deldefine extends Command {
    Config c;
    public Deldefine(){
        super("Deldefine", "Delete a Definition"+ "deldefine word");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (c.getAdmins().contains(e.getUser().getNick())) {
            StringBuilder sb = new StringBuilder();
            String[] arguments = e.getMessage().split(" ");
            for (int i = 2; i < arguments.length; i++) {
                sb.append(arguments[i]).append(" ");
            }
            String word = e.getMessage().split(" ")[1];
            DefineYML.delDefine(word);
            e.respond("Definition Deleted!");
            return true;
        } else {
            e.respond(c.getPermissionDenied());

            return false;
        }

    }
}
