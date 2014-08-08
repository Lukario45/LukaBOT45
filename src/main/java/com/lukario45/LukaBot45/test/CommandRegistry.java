/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukario45.LukaBot45.test;

import java.util.HashMap;

/**
 *
 * @author Kevin
 */
public class CommandRegistry {

    /**
     *
     */
    public static HashMap<String, Command> commands = new HashMap<>();

    public static void register(Command c) {
        if (commands.containsKey(c.getName())) {
            return;
        }
        commands.put(c.getName().toLowerCase(), c);
    }

    public static void unRegister(Command c) {
        commands.remove(c.getName().toLowerCase());
    }

    public static Command getCommand(String name) {
        if (commands.containsKey(name)) {
            return commands.get(name);
        } else {
            try {
                commands.put(name, (Command) Command.class.getClassLoader().loadClass("com.lukario45.LukaBot45.commands." + name).newInstance());
                return commands.get(name);
            } catch (Exception ex) {
                //Logger.getLogger(CommandRegistry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }
}
