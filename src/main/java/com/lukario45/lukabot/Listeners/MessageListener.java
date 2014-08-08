package com.lukario45.lukabot.Listeners;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.CommandRegistry;
import com.lukario45.lukabot.api.Config;
import org.apache.commons.lang.StringUtils;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.Arrays;

/**
 * Created by zack6849 on 8/8/14.
 */
public class MessageListener extends ListenerAdapter {
    private Config config;

    public MessageListener(Config config) {
        this.config = config;
    }

    public void onMessage(MessageEvent event) {
        if ((event.getMessage().startsWith(config.getPublicIdentifier()) || event.getMessage().startsWith(config.getNoticeIdentifier())) && event.getMessage().split(" ").length >= 1) {
            boolean isPublic = event.getMessage().startsWith(config.getPublicIdentifier());
            String command;
            if(isPublic){
                command = StringUtils.capitalize(event.getMessage().split(" ")[0].substring(config.getPublicIdentifier().length()));
            }else{
                command = StringUtils.capitalize(event.getMessage().split(" ")[0].substring(config.getNoticeIdentifier().length()));
            }
            String[] args = Arrays.copyOfRange(event.getMessage().split(" "), 1, event.getMessage().split(" ").length);
            Command cmd = CommandRegistry.getCommand(command);
            cmd.execute(event, args, isPublic);
        }
    }
}
