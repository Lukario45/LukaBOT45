package com.lukario45.lukabot.commands;

import com.lukario45.lukabot.api.Command;
import com.lukario45.lukabot.api.Config;
import org.apache.commons.lang.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by zack6849 on 8/8/14.
 */
public class Test extends Command {
    private Config config;

    public Test() {
        super("Test", "Test command", "no help required.");
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public boolean execute(MessageEvent event, String[] args, boolean isPublic) {
        event.getChannel().send().message(StringUtils.join(args, " "));
        return true;
    }
}
