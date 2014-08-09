package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.DefineYML;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Newdefine extends Command {
    Config c;

    public Newdefine() {
        super("newdefine", "LukaBots Definition maker!" , "newdefine word definition");
    }

    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (args.length > 1) {
            StringBuilder sb = new StringBuilder();
            String[] arguments = e.getMessage().split(" ");
            for (int i = 2; i < arguments.length; i++) {
                sb.append(arguments[i]).append(" ");
            }
            String word = e.getMessage().split(" ")[1];
            String allargs = sb.toString().trim();
            Boolean exists = DefineYML.hasDefinition(word);

            if (!exists && Utils.checkPerms(e ,c)) {
                DefineYML.addDefinition(word.toLowerCase(), allargs);
                e.respond("The Definition for " + word.toLowerCase() + " is " + allargs);
                return true;

            } else {
                e.respond("There is already a definition for that word! Or you don't have the permission to make a definition!");
                return false;
            }
        } else {
           // e.respond(getHelp());
            return false;
        }
    }

}
