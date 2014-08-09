package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Join  extends Command{
    Config config;
    public Join(){
        super("Join", "Joins a Channel", "Join #channel");
    }
    @Override
    public void setConfig(Config config) {
this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (args.length == 1) {
            if (config.getAdmins().contains(e.getUser().getNick())) {
                e.respond("k <3");
                e.getBot().sendIRC().joinChannel(args[0]);
                e.getBot().getUserChannelDao().getChannel(args[0]);
                return true;
            } else {
                e.respond(config.getPermissionDenied());
                return false;
            }

        }
        return false;
    }
}
