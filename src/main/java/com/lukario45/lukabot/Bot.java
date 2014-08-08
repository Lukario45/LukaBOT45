package com.lukario45.lukabot;

import com.lukario45.lukabot.api.Config;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zack6849 on 8/8/14.
 */
public class Bot {
    private static Config conf;
    public static void main(String[] args){
        System.out.println(System.getProperty("user.home"));
        conf = new Config();
        conf.loadConfiguration();
    }
    public static void start(){
        try{
        //Set logging settings for log4j because of the new update
        Configuration.Builder<PircBotX> config = new Configuration.Builder<PircBotX>();
        config.setServer(conf.getIrcServer(), 25565);
        config.setVersion(conf.getIdent());
        config.setLogin(conf.getRealname());
        config.setName(conf.getNickname());
        config.setFinger("Alphabot V2.0 BETA");
        config.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
        config.setNickservPassword(conf.getNickservPassword());
        config.setAutoReconnect(true);
        config.setAutoNickChange(true);
        for(String channel : conf.getChannels()){
            config.addAutoJoinChannel(channel);
        }
        PircBotX bot = new PircBotX(config.buildConfiguration());
        bot.startBot();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
