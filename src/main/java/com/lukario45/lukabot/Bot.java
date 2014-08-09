package com.lukario45.lukabot;

import com.lukario45.lukabot.Listeners.MessageListener;
import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.CommandRegistry;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.DefineYML;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.reflections.Reflections;
import org.slf4j.impl.SimpleLogger;

import java.nio.charset.Charset;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zack6849 on 8/8/14.
 */
public class Bot {
    private static Config conf;

    public static void main(String[] args) {
        //Set logging settings for log4j because of the new update
        System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true");
        System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "[HH:mm:ss]");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        conf = new Config();
        conf.loadConfiguration();
        start();
    }

    public static void start() {
        try {
            Reflections reflections = new Reflections(Bot.class.getPackage().getName() + ".commands");
            Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
            for (Class c : subTypes) {
                Command cmd = CommandRegistry.getCommand(c.getSimpleName());
                cmd.setConfig(conf);
                CommandRegistry.register(cmd);
            }
            DefineYML.load();
            Configuration.Builder<PircBotX> config = new Configuration.Builder<PircBotX>();
            config.setServer(conf.getIrcServer(), 6667);
            config.setVersion(conf.getIdent());
            config.setLogin(conf.getRealname());
            config.setName(conf.getNickname());
            config.setFinger("Alphabot V2.0 BETA");
            //attempt to use UTF-8 if possible
            config.setEncoding(Charset.isSupported("UTF-8") ? Charset.forName("UTF-8") : Charset.defaultCharset());
            config.setNickservPassword(conf.getNickservPassword());
            config.setAutoReconnect(true);
            config.setAutoNickChange(true);
            config.getListenerManager().addListener(new MessageListener(conf));
            for (String channel : conf.getChannels()) {
                System.out.println(channel);
                config.addAutoJoinChannel(channel);
            }
            PircBotX bot = new PircBotX(config.buildConfiguration());
            System.out.println("Starting...");
            bot.startBot();
            System.out.println("Shutting down..");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
