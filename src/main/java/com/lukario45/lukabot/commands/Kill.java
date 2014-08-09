package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.Channel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Kill extends Command {
    Config config;
    public Kill(){
        super("Kill", "Shuts the bot down", "Kill");
    }
    @Override

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if(config.getAdmins().contains(e.getUser().getNick())) {


            for (Channel chan : e.getBot().getUserChannelDao().getAllChannels()) {
                chan.send().part("Bot Killed!");
            }
            e.getBot().stopBotReconnect();
            e.getBot().sendIRC().quitServer("Shutting down...");
            return true;
        }
        else{
            e.respond(config.getPermissionDenied());
            return false;
        }

    }
}
