package com.lukario45.lukabot.api;

import com.lukario45.lukabot.Bot;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * Created by zack6849 on 8/8/14.
 */
public class CommandRegistry {

    public static HashMap<String, Command> commands = new HashMap<>();

    public static void register(Command command) {
        if (commands.containsKey(command.getName())) {
            return;
        }
        commands.put(command.getName(), command);
    }

    public static void unregister(Command command) {
        commands.remove(command.getName());
    }

    public static Command getCommand(String name) {
        if (commands.containsKey(name)) {
            return commands.get(name);
        }
        try {
            commands.put(name, (Command) Command.class.getClassLoader().loadClass(Bot.class.getPackage().getName() + ".commands." + StringUtils.capitalize(name)).newInstance());
            return commands.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
