package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.apache.commons.lang.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Say extends Command {
    Config c;
    public Say(){
        super("Say","Bot Repeats Arguments", "Say message");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if(args.length >= 1){
            if (Utils.checkPerms(e,c)) {
                e.getChannel().send().message(StringUtils.join(args, " "));
            }else{
                e.getUser().send().notice(c.getPermissionDenied());
            }
            return true;
        }
        return false;
    }
}
