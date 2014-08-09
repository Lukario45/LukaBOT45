package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Voice extends Command {
    private Config config;

    public Voice(){
        super("Voice", "Gives a user voice in a channel", "Voice <user> or Voice <channel> <user>");
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if(args.length >= 1){
            Channel targetchan = e.getChannel();
            if(args.length == 2){
                targetchan = e.getBot().getUserChannelDao().getChannel(args[0]);
            }
            if(Utils.getRank(targetchan, e.getUser()) > UserLevel.VOICE.ordinal() || config.getAdmins().contains(e.getUser().getNick())){
                if(args.length == 1){
                    User target = e.getBot().getUserChannelDao().getUser(args[0]);
                    targetchan.send().voice(target);
                }
                if(args.length == 2){
                    User target = e.getBot().getUserChannelDao().getUser(args[1]);
                    targetchan.send().voice(target);
                }
            }else{
                e.getUser().send().notice(config.getPermissionDenied());
            }
            return true;
        }
        return false;
    }
}
