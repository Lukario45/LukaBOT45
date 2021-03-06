package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

import javax.rmi.CORBA.Util;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Part extends Command {
    Config c;
    public Part(){
       super("Part", "Leaves Channel", "Part");
   }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.checkPerms(e, c) ) {
            e.getChannel().send().part();
            return true;
        } else {
            e.respond(c.getPermissionDenied());
            return true;
        }
    }
}
