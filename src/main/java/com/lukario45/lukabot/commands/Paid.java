package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import com.lukario45.lukabot.api.Utils;
import org.pircbotx.Colors;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Kevin on 8/8/2014.
 */
public class Paid extends Command {
    Config c;
    public Paid(){
        super("Paid", " Check if someone has paid for MC", "Paid name");
    }
    @Override
    public void setConfig(Config config) {
        this.c = config;
    }

    @Override
    public boolean execute(MessageEvent e, String[] args, boolean isPublic) {
        if (args.length == 1) {
            Boolean b = Utils.checkAccount(args[0]);

                if (b) {
                    e.respond(args[0] + Colors.GREEN + " has " + Colors.NORMAL + "paid for minecraft");
                    return true;
                } else {

                    e.respond(args[0] + Colors.RED + " has not " + Colors.NORMAL + "paid for minecraft");
                    return true;
                }
            }

       return false;


    }
}
