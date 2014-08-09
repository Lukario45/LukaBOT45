package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Prefix extends Command {
    private Config config;
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if(args.length == 1){
            if(config.getAdmins().contains(e.getChannel().getName())){
                config.setPublicIdentifier(args[0]);
                e.getUser().send().notice("Set prefix to '" + config.getPublicIdentifier() + "'");
            }else{
               e.getUser().send().notice(config.getPermissionDenied());
            }
            return true;
        }
        return false;
    }
}
