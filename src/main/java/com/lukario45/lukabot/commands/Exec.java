
/*
 *    This file is part of Alphabot.
 *
 *    Alphabot is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.lukario45.lukabot.commands;

import bsh.EvalError;
import com.lukario45.lukabot.api.*;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Exec extends Command {

    private static bsh.Interpreter interpreter;

    static {
        try {
            interpreter = new bsh.Interpreter();
            interpreter.getNameSpace().doSuperImport();
            interpreter.set("utils", new Utils());
            interpreter.set("Utils", interpreter.get("utils"));
            interpreter.set("registry", new CommandRegistry());
            if (System.getProperty("os.name").toLowerCase().contains("linux") || System.getProperty("os.name").toLowerCase().contains("mac")) {
                interpreter.eval("java.lang.String getStuff(java.lang.String command){ java.lang.String output = \"\";java.lang.Process p = java.lang.Runtime.getRuntime().exec(new java.lang.String[] {\"/bin/sh\", \"-c\", command}); java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));java.lang.String temp = \"\";while((temp = in.readLine()) != null){ output += temp + \"\\t\"; } return output; }");
            } else {
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    interpreter.eval("java.lang.String getStuff(java.lang.String command){java.lang.String output = \"\";java.lang.Process p = java.lang.Runtime.getRuntime().exec(command);java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));java.lang.String temp = \"\";while((temp = in.readLine()) != null){output += temp + \"\\t\";}return output;}");
                } else {
                    System.out.println("Unknown operating system detected, i have no idea how to make getStuff work here :D :D");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Config config;
    // private PermissionManager manager;

    public Exec() {
        super("Exec", "Execute java code at runtime", "Exec <code> ex. exec bot.sendMessage(chan, \"Hello world!\");");
    }

    @Override
    public boolean execute(MessageEvent event, String[] args, boolean isPublic) {
        if (Utils.checkPerms(event, config)) {
            //String[] args = event.getMessage().split(" ");
            //StringBuilder sb = new StringBuilder();
            if (args.length >= 1) {
                try {

                    interpreter.set("event", event);
                    interpreter.set("bot", event.getBot());
                    interpreter.set("chan", event.getChannel());
                    interpreter.set("user", event.getUser());
                    interpreter.set("config", config);
                    interpreter.set("conf", config);
                    //interpreter.set("manager", manager);
                    //for (int i = 1; i < args.length; i++) {
                   //     sb.append(args[i]).append(" ");
                   // }
                   // String command = sb.toString().trim();
                    //interpreter.eval(command);
                    interpreter.eval(StringUtils.join(args, " "));
                    return true;
                } catch (EvalError ex) {
                    Logger.getLogger(Exec.class.getName()).log(Level.SEVERE, null, ex);
                    event.getChannel().send().message(ex.toString());
                    return true;


                }
            }
        }
        return false;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
        try {
            interpreter.set("config", config);
            interpreter.set("conf", config);
        } catch (EvalError error) {
            error.printStackTrace();
        }
    }


}



