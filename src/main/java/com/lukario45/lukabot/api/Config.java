package com.lukario45.lukabot.api;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zack6849 on 8/8/14.
 */
public class Config {
    private PropertiesConfiguration conf;
    private boolean identifyWithNickserv;
    private boolean verifyAdminNicks;
    private boolean debug;
    private String permissionDenied;
    private String ircServer;
    private String notAdmin;
    private String nickservPassword;
    private String nickname;
    private String ident;
    private String realname;
    private String noticeIdentifier;
    private String publicIdentifier;
    private List<String> admins;
    private List<String> channels;

    public void loadConfiguration() {
        try {
            setConf(new PropertiesConfiguration(new File(System.getProperty("user.home") + File.separator + ".LukaBOT45", "config.yml")));
            if (!getConf().getFile().exists()) {
                getConf().getFile().getParentFile().mkdirs();
                getConf().setProperty("SERVER", "irc.esper.net");
                getConf().setProperty("BOT-NICKNAME", "LukaBOT45");
                getConf().setProperty("BOT-IDENT", "Bot");
                getConf().setProperty("BOT-REALNAME", "LukaBOT45");
                getConf().setProperty("IDENTIFY-WITH-NICKSERV", true);
                getConf().setProperty("NICKSERV-PASS", "password");
                getConf().setProperty("CHANNELS", "#alphacraft #alphabot");
                getConf().setProperty("VERIFY-ADMIN-NICKS", true);
                getConf().setProperty("ADMIN-NICKS", "zack6849 zack6849|Offline Lukario45 Lukario45-BNC" );
                getConf().setProperty("PUBLIC-IDENTIFIER", "$");
                getConf().setProperty("NOTICE-IDENTIFIER", "|");
                getConf().setProperty("PERMISSIONS-DENIED", "Sorry you lack the necessary permissions to perform that action.");
                getConf().setProperty("NOT_ADMIN", "Sorry, you are either not on the admins list or are not logged in as an admin.");
                getConf().setProperty("DEBUG_MODE", false);
                getConf().save();
            }
            getConf().load();
            setAdmins(new ArrayList<String>());
            setChannels(new ArrayList<String>());
            setIrcServer(getConf().getString("SERVER"));
            setNickname(getConf().getString("BOT-NICKNAME"));
            setIdent(getConf().getString("BOT-IDENT"));
            setRealname(getConf().getString("BOT-REALNAME"));
            setIdentifyWithNickserv(getConf().getBoolean("IDENTIFY-WITH-NICKSERV"));
            setNickservPassword(getConf().getString("NICKSERV-PASS"));
            setVerifyAdminNicks(getConf().getBoolean("VERIFY-ADMIN-NICKS"));
            setPublicIdentifier(getConf().getString("PUBLIC-IDENTIFIER"));
            setNoticeIdentifier(getConf().getString("NOTICE-IDENTIFIER"));
            setPermissionDenied(getConf().getString("PERMISSIONS-DENIED"));
            setNotAdmin(getConf().getString("NOT-ADMIN"));
            setDebug(getConf().getBoolean("DEBUG_MODE"));
            //why a this instead of Arrays.asList you might ask? because if you do that you can't edit the list at runtime without a ConcurrentModificationException!
            //IDEA suggested replacing my for loop with this, let's hope it doesnt throw a CME as well? :D
            Collections.addAll(getAdmins(), getConf().getString("ADMIN-NICKS").split(" "));
            Collections.addAll(getChannels(), getConf().getString("CHANNELS").split(" "));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public PropertiesConfiguration getConf() {
        return conf;
    }

    public void setConf(PropertiesConfiguration conf) {
        this.conf = conf;
    }

    public boolean isIdentifyWithNickserv() {
        return identifyWithNickserv;
    }

    public void setIdentifyWithNickserv(boolean identifyWithNickserv) {
        this.identifyWithNickserv = identifyWithNickserv;
    }

    public boolean isVerifyAdminNicks() {
        return verifyAdminNicks;
    }

    public void setVerifyAdminNicks(boolean verifyAdminNicks) {
        this.verifyAdminNicks = verifyAdminNicks;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getPermissionDenied() {
        return permissionDenied;
    }

    public void setPermissionDenied(String permissionDenied) {
        this.permissionDenied = permissionDenied;
    }

    public String getIrcServer() {
        return ircServer;
    }

    public void setIrcServer(String ircServer) {
        this.ircServer = ircServer;
    }

    public String getNotAdmin() {
        return notAdmin;
    }

    public void setNotAdmin(String notAdmin) {
        this.notAdmin = notAdmin;
    }

    public String getNickservPassword() {
        return nickservPassword;
    }

    public void setNickservPassword(String nickservPassword) {
        this.nickservPassword = nickservPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNoticeIdentifier() {
        return noticeIdentifier;
    }

    public void setNoticeIdentifier(String noticeIdentifier) {
        this.noticeIdentifier = noticeIdentifier;
    }

    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }
}
