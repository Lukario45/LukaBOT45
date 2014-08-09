package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Mcstatus extends Command {
    Config c;
    public Mcstatus(){
        super("MCStatus","Checks Minecraft Servers", "MCStatus");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {





            try {
                URL url;
                url = new URL("http://status.mojang.com/check");
                BufferedReader re = new BufferedReader(new InputStreamReader(url.openStream()));
                String st;
                while ((st = re.readLine()) != null) {
                    String a = st.replace("red", Colors.RED + "Offline" + Colors.NORMAL).replace("green", Colors.GREEN + "Online" + Colors.NORMAL).replace("[", "").replace("]", "");
                    String b = a.replace("{", "").replace("}", "").replace(":", " is currently ").replace("\"", "").replaceAll(",", ", ");
                    e.getChannel().send().message( b);
                    return true;
                }
            } catch (IOException E) {
                if (E.getMessage().contains("503")) {
                    e.getChannel().send().message( "The minecraft status server is temporarily unavailable, please try again later");
                    return false;
                }
                if (E.getMessage().contains("404")) {
                    e.getChannel().send().message( "Uhoh, it would appear as if the haspaid page has been removed or relocated >_>");
                    return false;
                }
            }

        return false;
    }
}
