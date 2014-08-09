package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.UrbanParser;
import org.pircbotx.hooks.events.MessageEvent;


import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Urban extends Command {
    Config config;
    UrbanParser urban;

    public Urban() {
        super("Urban", "Define From Urban Dictionary", "urban term entry-number");
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        try {
            String[] term = args;

            String[] urbanDictionary = urban.search(term[0], term[1]).split(":");
            int entry = Integer.parseInt(urbanDictionary[0]) + 1;
            e.respond("Entry #" + entry + " Word: " + urbanDictionary[1]);
            e.respond("Definition: " + urbanDictionary[2]);
            e.respond("Example: " + urbanDictionary[3]);
        } catch (IOException ex) {
            //Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            e.respond("Usage: $urban word entrynumber");
        } catch (IndexOutOfBoundsException ex) {
            e.respond("I'm sorry, but I couldn't find a definition for that word on Urban Dictionary!");
        }
        return true;
    }

}

