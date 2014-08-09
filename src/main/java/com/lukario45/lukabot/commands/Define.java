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
        if(args.length == 1){
         if(!DefineYML.hasDefinition(args[0])){
             e.respond("Sorry, i don't have a definition for that! D:");
         }else{
             e.respond(args[0] + ": " + DefineYML.getDefinition(args[0]));
         }
         return true;
        }
        //wrong args / usage, return false.
        return false;
    }

}

