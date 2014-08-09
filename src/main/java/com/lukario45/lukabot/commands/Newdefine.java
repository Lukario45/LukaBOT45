package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.DefineYML;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Newdefine extends Command {
    Config c;
    public Newdefine(){
        super("newdefine","LukaBots Definition maker!"+"newdefine word definition");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if(args.length >= 2){
            return false;
        }
        String word = args[0];
        String allargs = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");

        if (c.getAdmins().contains(e.getUser().getNick())) {
            if(!DefineYML.hasDefinition(word)){
                DefineYML.addDefinition(word.toLowerCase(), allargs);
                e.respond("The Definition for " + word.toLowerCase() + " is " + allargs);
                return true;
            }else{
                e.respond("There's already a definition for this word set, please remove the old one first! ");
            }
        } else {
            e.respond(c.getPermissionDenied());
            return false;
        }
        return true;
    }
}
