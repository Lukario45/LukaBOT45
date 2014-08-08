/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import static test.Bot.ignored;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author Kevin
 */
public class CommandListener {

    public CommandListener() {
    }

    public static void listen(MessageEvent e) {
        if (e.getMessage().startsWith(test.Config.PUBLIC_IDENTIFIER) || e.getMessage().startsWith(test.Config.NOTICE_IDENTIFIER) || e.getMessage().toLowerCase().startsWith(e.getBot().getNick().toLowerCase() + ", ")) {
            if (ignored.contains(e.getUser().getHostmask())) {
                test.Commands.sendNotice(e.getUser(), "Sorry, you've been ignored by the bot.");
                return;
            }
        }
    }
}
