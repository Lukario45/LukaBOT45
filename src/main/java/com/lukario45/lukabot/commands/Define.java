package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.DefineYML;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Define extends Command {
    Config c;

    public Define() {
        super("Define", "LukaBOT's own Definitions!", "define word");
    }

    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        String word = args[0];

        DefineYML.getDefine(word.toLowerCase());

        if (DefineYML.defin==null) {
            e.respond("I do not have a definition for that :( sorry!");
            return false;

        } else {
            e.respond(word + ": " + DefineYML.defin);
            return true;
        }
    }

}

