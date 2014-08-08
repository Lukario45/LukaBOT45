/*
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author zack6849(zcraig29@gmail.com)
 */
public class Commands {

    static List<String> owners = Bot.owners;
    static String perms = Config.PERMISSIONS_DENIED;
    static String password;

    public static void shortenUrl(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args.length == 2) {
            String longurl = args[1];
            e.respond(Utils.shortenUrl(args[1]));
        } else {
            Utils.sendNotice(e.getUser(), "Improper usage! correct usage: $shorten http://google.com/");
        }
    }

    public static void listOperators(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        String s = String.valueOf(args[0].charAt(0));
        if (s.equalsIgnoreCase(Config.PUBLIC_IDENTIFIER)) {
            List<String> myList = new ArrayList<String>();
            for (User u : e.getChannel().getOps()) {
                myList.add(u.getNick());
            }
            String f1 = myList.toString().replaceAll("[\\['']|['\\]'']", "");
            e.respond("The current channel operators are " + f1);
        }
        if (s.equalsIgnoreCase(Config.NOTICE_IDENTIFIER)) {
            List<String> myList = new ArrayList<String>();
            for (User u : e.getChannel().getOps()) {
                myList.add(u.getNick());
            }
            String f1 = myList.toString().replaceAll("[\\['']|['\\]'']", "");
            sendNotice(e.getUser(), "The current channel operators are " + f1);
        }
    }

    public static void joinChannel(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args.length == 2) {
            if (e.getBot().getChannel(args[1]).isOp(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
                e.respond("k <3");
                e.getBot().joinChannel(args[1]);
                e.getBot().getChannel(args[1]);
            } else {
                e.respond(perms);
            }
        }
    }

    public static void setDelay(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args.length == 2) {
            if (StringUtils.isNumeric(args[1])) {
                e.getBot().setMessageDelay(Integer.valueOf(args[1]));
                sendNotice(e.getUser(), "Message delay set to " + Integer.valueOf(args[1]) + " milliseconds!");
            } else {
                sendNotice(e.getUser(), "The argument " + args[1] + " is not a number!");
            }
        }
    }

    public static void authenticate(MessageEvent e) {
        e.getBot().identify(password);
    }

    public static void globalSay(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String all = sb.toString().trim();
        if (args.length >= 3) {
            Channel t = e.getBot().getChannel(args[1]);
            e.getBot().sendMessage(t, all);
        } else {
            sendNotice(e.getUser(), "Usage: " + Bot.prefix + "GSAY #CHANNEL MESSAGE");
        }
    }

    public static void slap(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (args.length == 2) {
            User t = e.getBot().getUser(args[1]);
            e.getBot().sendAction(e.getChannel(), "slaps " + t.getNick() + " around a bit with a stack trace");
        } else {
            sendNotice(e.getUser(), "Usage: " + Bot.prefix + "slap <username>");
        }
    }

    public static void partChannel(MessageEvent e) {
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            e.getBot().partChannel(e.getChannel());
        } else {
            e.respond(perms);
        }
    }

    public static void nope(MessageEvent e) {
        if (e.getMessage().contains("@")) {
            String[] arg1 = e.getMessage().split("@");
            e.getBot().sendMessage(e.getChannel(), arg1[1] + ": http://www.youtube.com/watch?v=gvdf5n-zI14");
        } else {
            e.getBot().sendMessage(e.getChannel(), "nope: http://www.youtube.com/watch?v=gvdf5n-zI14");
        }
    }

    public static void changeNickName(MessageEvent e) {
        String[] arguments = e.getMessage().split(" ");
        if (Utils.isAdmin(e.getUser().getNick())) {
            if (arguments.length == 2) {
                e.getBot().changeNick(arguments[1]);
            } else {
                e.respond("Usage: nick <new nickname>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void setPrefix(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (e.getChannel().getVoices().contains(e.getUser()) || e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            if (args.length == 2) {
                Bot.prefix = args[1];
                sendNotice(e.getUser(), e.getBot().getNick() + "' prefix was set to :" + Bot.prefix);
            } else {
                sendNotice(e.getUser(), "Usage: $Bot prefix <new Bot prefix>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void listChannels(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        String s = String.valueOf(args[0].charAt(0));
        if (s.equalsIgnoreCase(Config.PUBLIC_IDENTIFIER)) {
            e.respond(e.getBot().getNick() + " is currently in the following channels :" + Utils.removeBrackets(e.getBot().getChannelsNames().toString()));
        }
        if (s.equalsIgnoreCase(Config.NOTICE_IDENTIFIER)) {
            sendNotice(e.getUser(), e.getBot().getNick() + " is currently in the following channels :" + Utils.removeBrackets(e.getBot().getChannelsNames().toString()));
        }
    }

    public static void checkAccount(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        String s = String.valueOf(args[0].charAt(0));
        if (args.length == 2) {
            Boolean b = Utils.checkAccount(args[1]);
            if (s.equalsIgnoreCase(Config.PUBLIC_IDENTIFIER)) {
                if (b) {
                    e.respond(args[1] + Colors.GREEN + " has " + Colors.NORMAL + "paid for minecraft");
                } else {
                    e.respond(args[1] + Colors.RED + " has not " + Colors.NORMAL + "paid for minecraft");
                }
            }
            if (s.equalsIgnoreCase(Config.NOTICE_IDENTIFIER)) {
                if (b) {
                    Utils.sendNotice(e.getUser(), args[1] + ":" + Colors.GREEN + String.valueOf(b));
                } else {
                    Utils.sendNotice(e.getUser(), args[1] + ":" + Colors.RED + String.valueOf(b));
                }
            }
        } else {
            Utils.sendNotice(e.getUser(), "You failed to specify a username! usage:  " + Bot.prefix + "paid <username>");
        }

    }

    public static void checkMojangServers(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        String s = String.valueOf(args[0].charAt(0));
        if (s.equalsIgnoreCase(Config.PUBLIC_IDENTIFIER)) {
            try {
                URL url;
                url = new URL("http://status.mojang.com/check");
                BufferedReader re = new BufferedReader(new InputStreamReader(url.openStream()));
                String st;
                while ((st = re.readLine()) != null) {
                    String a = st.replace("red", Colors.RED + "Offline" + Colors.NORMAL).replace("green", Colors.GREEN + "Online" + Colors.NORMAL).replace("[", "").replace("]", "");
                    String b = a.replace("{", "").replace("}", "").replace(":", " is currently ").replace("\"", "").replaceAll(",", ", ");
                    e.respond(b);
                }
            } catch (IOException E) {
                if (E.getMessage().contains("503")) {
                    e.respond("The minecraft status server is temporarily unavailable, please try again later");
                }
                if (E.getMessage().contains("404")) {
                    e.respond("Uhoh, it would appear as if the haspaid page has been removed or relocated >_>");
                }
            }
        }
        if (s.equalsIgnoreCase(Config.NOTICE_IDENTIFIER)) {
            try {
                URL url;
                url = new URL("http://status.mojang.com/check");
                BufferedReader re = new BufferedReader(new InputStreamReader(url.openStream()));
                String st;
                while ((st = re.readLine()) != null) {
                    String a = st.replace("red", Colors.RED + "Offline" + Colors.NORMAL).replace("green", Colors.GREEN + "Online" + Colors.NORMAL).replace("[", "").replace("]", "");
                    String b = a.replace("{", "").replace("}", "").replace(":", " is currently ").replace("\"", "").replaceAll(",", ", ");
                    sendNotice(e.getUser(), b);
                }
            } catch (IOException E) {
                if (E.getMessage().contains("503")) {
                    sendNotice(e.getUser(), "The minecraft status server is temporarily unavailable, please try again later");
                }
                if (E.getMessage().contains("404")) {
                    sendNotice(e.getUser(), "Uhoh, it would appear as if the haspaid page has been removed or relocated >_>");
                }
            }
        }
    }

    public static void say(MessageEvent e) {
        if (Utils.isAdmin(e.getUser().getNick())) {
            StringBuilder sb = new StringBuilder();
            String[] arguments = e.getMessage().split(" ");
            for (int i = 1; i < arguments.length; i++) {
                sb.append(arguments[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            e.getBot().sendMessage(e.getChannel(), allArgs);
        } else {
            e.respond(perms);
        }
    }

    public static void checkServerStatus(MessageEvent e) {
        String[] args = e.getMessage().split(" ");

        String s1 = String.valueOf(e.getMessage().charAt(0));
        String result = null;
        int port = Integer.parseInt(args[2]);
        String address = args[1];
        try {
            result = Utils.checkServerStatus(InetAddress.getByName(address), port);
        } catch (UnknownHostException ex) {
        }
        // if (args.length == 2) {
        //     try {
        //         result = Utils.checkServerStatus(InetAddress.getByName(args[1]));
        //      } catch (UnknownHostException ex) {
        //          Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        //      }
        //  } else if (args.length == 3) {
        //      try {
        //           result = Utils.checkServerStatus(InetAddress.getByName(args[1]), Integer.valueOf(args[2]));
        //       } catch (UnknownHostException ex) {
        //         Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        //     }
        // }
        //  if (s1.equalsIgnoreCase(Config.PUBLIC_IDENTIFIER)) {
        //     Utils.sendNotice(e.getUser(), result);
        //  }
        ///  if (s1.equalsIgnoreCase(Config.NOTICE_IDENTIFIER)) {
        //     e.getBot().sendMessage(e.getChannel(), result);
        //  }
    }

    public static void kill(MessageEvent e) {

        for (Channel ch : e.getBot().getChannels()) {
            e.getBot().partChannel(ch, "Killed by " + e.getUser().getNick() + " in " + e.getChannel().getName());
        }
        e.getBot().disconnect();
        e.getBot().shutdown();
    }

    public static void urban(MessageEvent e) {
        try {
            String[] term = e.getMessage().split(" ");

            String[] urbanDictionary = Urban.search(term[1], term[2]).split(":");
            int entry = Integer.parseInt(urbanDictionary[0]) + 1;
            e.respond("Entry #" + entry + " Word: " + urbanDictionary[1]);
            e.respond("Definition: " + urbanDictionary[2]);
            e.respond("Example: " + urbanDictionary[3]);
        } catch (IOException ex) {
            Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            e.respond("Usage: $urban word entrynumber");
        } catch (IndexOutOfBoundsException ex) {
            e.respond("I'm sorry, but I couldn't find a definition for that word on Urban Dictionary!");
        }

    }

    public static void op(MessageEvent e) {
        String[] arguments = e.getMessage().split(" ");
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().op(e.getChannel(), u);
            } else {
                e.respond("Usage: op <username>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void deop(MessageEvent e) {
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            String[] arguments = e.getMessage().split(" ");
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().sendMessage(e.getChannel(), "Sorry " + u.getNick() + " </3");
                e.getBot().deOp(e.getChannel(), u);
            } else {
                e.respond("Usage: deop <username>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void voice(MessageEvent e) {
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            String[] arguments = e.getMessage().split(" ");
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().voice(e.getChannel(), u);
            } else {
                e.respond("Usage: voice <username>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void deVoice(MessageEvent e) {
        if (e.getChannel().getOps().contains(e.getUser()) || Utils.isAdmin(e.getUser().getNick())) {
            String[] arguments = e.getMessage().split(" ");
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().deVoice(e.getChannel(), u);
            } else {
                e.respond("Usage: devoice <username>");
            }
        } else {
            e.respond(perms);
        }
    }

    public static void quiet(MessageEvent e) {
        String[] arguments = e.getMessage().split(" ");
        if (e.getChannel().getOps().contains(e.getUser()) || isOwner(e.getUser()) || e.getChannel().hasVoice(e.getUser())) {
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().setMode(e.getChannel(), "+q " + u.getNick());
            } else {
                e.getBot().sendNotice(e.getUser(), "Usage: quiet <username>");
            }
        } else {
            e.getBot().sendNotice(e.getUser(), perms);
        }
    }

    public static void unquiet(MessageEvent e) {
        String[] arguments = e.getMessage().split(" ");
        if (e.getChannel().isOp(e.getUser()) || e.getChannel().hasVoice(e.getUser()) || isOwner(e.getUser())) {
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                e.getBot().setMode(e.getChannel(), "-q " + u.getNick());
            } else {
                e.getBot().sendNotice(e.getUser(), "Usage: unquiet <username>");
            }
        } else {
            e.getBot().sendNotice(e.getUser(), perms);
        }
    }

    public static void addOwner(MessageEvent e) {
        if (isOwner(e.getUser())) {
            String[] arguments = e.getMessage().split(" ");
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                if (!owners.contains(u.getNick())) {
                    owners.add(u.getNick());
                    e.getBot().sendNotice(e.getUser(), u.getNick() + " was added to the list of owners!");
                } else {
                    if (!owners.contains(arguments[1])) {
                        owners.remove(arguments[1]);
                        e.getBot().sendNotice(e.getUser(), arguments[1] + " was removed from the owners list");
                    } else {
                        e.getBot().sendNotice(e.getUser(), u.getNick() + " is already an owner!");
                    }
                }
            } else {
                e.getBot().sendNotice(e.getUser(), "Usage: addowner <name>");
            }
        } else {
            sendNotice(e.getUser(), perms);
        }
    }

    public static void removeOwner(MessageEvent e) {
        if (Utils.isAdmin(e.getUser().getNick())) {
            String[] arguments = e.getMessage().split(" ");
            if (arguments.length == 2) {
                User u = e.getBot().getUser(arguments[1]);
                if (isOwner(e.getUser())) {
                    owners.remove(u.getNick());
                    e.respond(u.getNick() + " was removed from the list of owners!");
                } else {
                    if (owners.contains(arguments[2])) {
                        owners.remove(arguments[2]);
                        e.respond(arguments[2] + " was removed from the list of owners");
                    }
                    e.respond(u.getNick() + " is not on the list of owners");
                }
            } else {
                e.respond("Usage: addowner <name>");
            }
        } else {
            sendNotice(e.getUser(), perms);
        }
    }

    public static void kick(MessageEvent e) {
        if (e.getMessage().toLowerCase().startsWith(Bot.prefix + "kick")) {
            String[] args = e.getMessage().split(" ");
            if (args.length <= 2) {
                User u = e.getBot().getUser(args[1]);
                if (e.getChannel().isOp(u) || isOwner(e.getUser()) || e.getChannel().hasVoice(e.getUser())) {
                    e.getBot().kick(e.getChannel(), u, "Kick requested by " + e.getUser().getNick());
                }
            }
            if (args.length >= 3) {
                User u = e.getBot().getUser(args[1]);
                if (!e.getChannel().isOp(u) && !e.getChannel().hasVoice(u)) {
                    StringBuilder sb = new StringBuilder();
                    String[] arguments = e.getMessage().split(" ");
                    for (int i = 2; i < arguments.length; i++) {
                        sb.append(arguments[i]).append(" ");
                    }
                    String allArgs = sb.toString().trim();
                    e.getBot().kick(e.getChannel(), u, allArgs);
                }
            }
        }
    }

    public static boolean isOwner(User u) {
        if (owners.contains(u.getNick()) && u.isVerified()) {
            return true;
        } else {
            return false;
        }
    }

    public static void sendNotice(User user, String notice) {
        Bot.bot.sendNotice(user, notice);
    }

    public static void ignore(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (owners.contains(e.getUser()) && e.getUser().isVerified() || e.getChannel().getOps().contains(e.getUser())) {
            if (args.length == 2) {
                User user = e.getBot().getUser(args[1]);
                if (!Bot.ignored.contains(user.getHostmask())) {
                    Bot.ignored.add(user.getHostmask());
                    e.respond(user.getHostmask() + " was added to the ignore list.");
                } else {
                    e.respond(user.getHostmask() + " is already in the ignore list");
                }
            } else {
                sendNotice(e.getUser(), "usage: $ignore user");
            }
        } else {
            sendNotice(e.getUser(), Commands.perms);
        }
    }

    public static void unignore(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        if (owners.contains(e.getUser()) && e.getUser().isVerified() || e.getChannel().getOps().contains(e.getUser())) {
            if (args.length == 2) {
                String s = args[1];
                if (Bot.ignored.contains(args[1])) {
                    Bot.ignored.remove(args[1]);
                    e.respond(args[1] + " was removed to the ignore list.");
                } else {
                    e.respond(args[1] + " is not in the ignore list");
                }
            } else {
                sendNotice(e.getUser(), "usage: $unignore user");
            }
        } else {
            sendNotice(e.getUser(), Commands.perms);
        }
    }

    public static void eat(MessageEvent e) {
        String eaten = e.getMessage().split("eat")[1];
        e.getBot().sendMessage(e.getChannel(), e.getUser().getNick() + " has eaten" + eaten);

    }

    public static void ping(MessageEvent e) {
        String[] args = e.getMessage().split(" ");
        e.getBot().sendMessage(e.getChannel(), ">pong");
    }

    public static void newDefine(MessageEvent e) {
        StringBuilder sb = new StringBuilder();
        String[] arguments = e.getMessage().split(" ");
        for (int i = 2; i < arguments.length; i++) {
            sb.append(arguments[i]).append(" ");
        }
        String word = e.getMessage().split(" ")[1];
        String allargs = sb.toString().trim();
        Boolean exists = Define.ifExists(word);

        if (!exists) {
            Define.addDefine(word.toLowerCase(), allargs);
            e.respond("The Definition for " + word.toLowerCase() + " is " + allargs);

        } else {
            e.respond("There is already a definition for that word!");
        }


    }

    public static void delDefine(MessageEvent e) {
        StringBuilder sb = new StringBuilder();
        String[] arguments = e.getMessage().split(" ");
        for (int i = 2; i < arguments.length; i++) {
            sb.append(arguments[i]).append(" ");
        }
        String word = e.getMessage().split(" ")[1];
        Define.delDefine(word);
        e.respond("Definition Deleted!");


    }

    public static void define(MessageEvent e) {
        String word = e.getMessage().split(" ")[1];

        Define.getDefine(word.toLowerCase());

        if (Define.defin == null) {
            e.respond("I do not have a definition for that :( sorry!");

        } else {
            e.respond(word + ": " + Define.defin);
        }





    }

    public static void login(MessageEvent e) {

        String password = e.getMessage().split(" ")[1];
        Bot.bot.identify(password);
        e.respond("Logged in <3");
    }
}
