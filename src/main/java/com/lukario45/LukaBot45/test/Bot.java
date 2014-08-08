package com.lukario45.lukabot45.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.reflections.Reflections;

@SuppressWarnings("rawtypes")
public class Bot extends ListenerAdapter {

    public static List<String> owners = new ArrayList<String>();
    public static PircBotX bot;
    public static String prefix = "$";
    private static List<String> allowed;
    public static List<String> ignored = new ArrayList<String>();

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        try {
            Reflections re = new Reflections("com.lukario45.LukaBot45.commands");
            Set<Class<? extends Command>> subTypes = re.getSubTypesOf(Command.class);
            for (Class c : subTypes) {
                Command cmd = CommandRegistry.getCommand(c.getSimpleName());
                System.out.println("Registered command " + cmd.getName() + " as key " + c.getSimpleName());
                CommandRegistry.register(cmd);
            }
            bot = new PircBotX();
            Config.loadConfig();
            Define.loadDefine();
            bot.setName(Config.NICK);
            bot.setVerbose(Config.DEBUG_MODE);
            bot.setLogin(Config.IDENT);
            bot.connect(Config.SERVER);
            bot.identify(Config.PASSWORD);
            allowed = Arrays.asList(Config.ADMINS);
            for (String s : Config.CHANS) {
                bot.joinChannel(s);
                System.out.println("Joined channel " + s);
            }
            bot.getListenerManager().addListener(new Bot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(MessageEvent e) {
        if (!e.getMessage().startsWith(Config.PUBLIC_IDENTIFIER) && !e.getMessage().startsWith(Config.NOTICE_IDENTIFIER)) {
            String format = String.format("[%s] %s: %s", e.getChannel().getName(), e.getUser().getNick(), e.getMessage());
            //Bukkit.broadcastMessage(format);
        }
        String command = CheckCommand(e);

        String title = null;
        String[] words = e.getMessage().split(" ");
        for (int i = 0; i < words.length; i++) {
            if (Utils.isUrl(words[i]) && !command.contains("shorten")) {
                title = Utils.getWebpageTitle(words[i]);
                String msg = String.format("%s's url title: %s", e.getUser().getNick(), title);
                e.getBot().sendMessage(e.getChannel(), msg);
            }
        }

        //System.
        /**
         * if (e.getMessage().startsWith(Config.PUBLIC_IDENTIFIER) ||
         * e.getMessage().startsWith(Config.NOTICE_IDENTIFIER) ||
         * e.getMessage().toLowerCase().startsWith(e.getBot().getNick().toLowerCase()
         * + ", ")) { if (ignored.contains(e.getUser().getHostmask())) {
         * Commands.sendNotice(e.getUser(), "Sorry, you've been ignored by the
         * bot."); return; } //if (command.equalsIgnoreCase("exec")) { //
         * Commands.executeCommand(e); //} if
         * (command.equalsIgnoreCase("shorten")) { Commands.shortenUrl(e); } if
         * (command.equalsIgnoreCase("listops")) { Commands.listOperators(e); }
         * if (command.equalsIgnoreCase("join")) { Commands.joinChannel(e); } if
         * (command.equalsIgnoreCase("delay")) { Commands.setDelay(e); } if
         * (command.equalsIgnoreCase("auth")) { Commands.authenticate(e); } if
         * (command.equalsIgnoreCase("gsay")) { Commands.globalSay(e); } if
         * (command.equalsIgnoreCase("slap")) { Commands.slap(e); } if
         * (command.equalsIgnoreCase("part")) { Commands.partChannel(e); } if
         * (command.equalsIgnoreCase("nope")) { Commands.nope(e); } if
         * (command.equalsIgnoreCase("nick")) { Commands.changeNickName(e); } if
         * (command.equalsIgnoreCase("chans")) { Commands.listChannels(e); } if
         * (command.equalsIgnoreCase("paid")) { Commands.checkAccount(e); } if
         * (command.equalsIgnoreCase("mcstatus")) {
         * Commands.checkMojangServers(e); }
         *
         *
         * if (command.equalsIgnoreCase("query")) {
         * Commands.checkServerStatus(e); } if
         * (command.equalsIgnoreCase("kill")) { Commands.kill(e); } if
         * (command.equalsIgnoreCase("op")) { Commands.op(e); } if
         * (command.equalsIgnoreCase("deop")) { Commands.deop(e); } if
         * (command.equalsIgnoreCase("voice")) { Commands.voice(e); } if
         * (command.equalsIgnoreCase("devoice")) { Commands.deVoice(e); } if
         * (command.equalsIgnoreCase("quiet")) { Commands.quiet(e); } if
         * (command.equalsIgnoreCase("unquiet")) { Commands.unquiet(e); } if
         * (command.equalsIgnoreCase("stack")) { // } if
         * (command.equalsIgnoreCase("login")) { Commands.login(e); } if
         * (command.equalsIgnoreCase("ignore")) { Commands.ignore(e); } if
         * (command.equalsIgnoreCase("unignore")) { Commands.unignore(e); } if
         * (command.equalsIgnoreCase("shorten")) { Commands.shortenUrl(e); } if
         * (command.equalsIgnoreCase("say")) { Commands.say(e); } if
         * (command.equalsIgnoreCase("eat")) { Commands.eat(e); } if
         * (command.equalsIgnoreCase("ping")) { Commands.ping(e); } if
         * (command.equalsIgnoreCase("newdef")) { Commands.newDefine(e); } if
         * (command.equalsIgnoreCase("define")) { Commands.define(e); } if
         * (command.equalsIgnoreCase("deldef")) { Commands.delDefine(e); } if
         * (command.equalsIgnoreCase("urban")) { Commands.urban(e); }
         */
        // }
    }

    @Override
    public void onJoin(JoinEvent e) {
        if (!e.getUser().getNick().equals(e.getBot().getNick())) {
            int ops = e.getChannel().getOps().size();
            int voices = e.getChannel().getVoices().size();
            int users = e.getChannel().getUsers().size() - voices - ops;
            String message = String.format("Hey there %s, weclome to %s! there are currently " + ops + "operators, " + voices + " voiced users, and "
                    + users + " normal users the current time is ", e.getUser().getNick());
            Commands.sendNotice(e.getUser(), message);
        }
    }

    public String CheckCommand(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        String s = String.valueOf(args[0].charAt(0));
        String command = args[0].replaceAll("\\$", "").replaceAll("\\|", "");
        return command;
    }

    public static String findUrl(MessageEvent e) {
        String msg = null;
        String title = null;
        String[] words = e.getMessage().split(" ");
        for (int i = 0; i < words.length; i++) {
            if (Utils.isUrl(words[i])) {
                title = Utils.getWebpageTitle(words[i]);
            }
        }
        msg = String.format("%s's url title: %s", e.getUser().getNick(), title);
        return msg;
    }

    public static List<String> getAccessList() {
        return allowed;
    }

    public static void refreshAcessList() {
        try {
            allowed.clear();
            URL u;
            u = new URL("https://dl.dropbox.com/u/49928817/bot_users.txt");
            BufferedReader b = new BufferedReader(new InputStreamReader(u.openConnection().getInputStream()));
            String[] names = b.readLine().toLowerCase().split(" ");
            b.close();
            allowed.addAll(Arrays.asList(names));
        } catch (Exception e) {
            User u = bot.getUser("zack6849");
            bot.sendNotice(u, "Error whilst fetching acess list! " + e.getCause() + " " + e.getMessage());
        }
    }
}
