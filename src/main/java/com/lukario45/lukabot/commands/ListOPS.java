package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 8/8/2014.
 */
public class ListOPS extends Command {
    Config config;
    public ListOPS(){
        super("ListOPS","List Channel Operators", "ListOPS");
    }
    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {

        String s = String.valueOf(args[0
                ].charAt(0));
        if (s.equalsIgnoreCase(config.getPublicIdentifier())) {
            List<String> myList = new ArrayList<String>();
            for (User u : e.getChannel().getOps()) {
                myList.add(u.getNick());
            }
            String f1 = myList.toString().replaceAll("[\\['']|['\\]'']", "");
            e.respond("The current channel operators are " + f1);
        }
        if (s.equalsIgnoreCase(config.getNoticeIdentifier())) {
            List<String> myList = new ArrayList<String>();
            for (User u : e.getChannel().getOps()) {
                myList.add(u.getNick());
            }
            String f1 = myList.toString().replaceAll("[\\['']|['\\]'']", "");
            //(e.getUser(), "The current channel operators are " + f1);
            e.getUser().send().notice("The current channel operators are " + f1);
        }
    return true;
    }
}
