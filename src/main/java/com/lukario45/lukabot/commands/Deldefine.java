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
            if(args.length == 1) {
                String word = args[0];
                DefineYML.delDefinition(word);
                e.respond("Definition Deleted!");
                return true;
            }
            return false;
        } else {
            e.respond(c.getPermissionDenied());
            return true;
        }
    }
}
