package test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {
	private static PropertiesConfiguration conf;
	public static File cnf = new File("C:\\Users\\Kevin\\.LukaBOT45\\config.yml");
	public static boolean IDENTIFY_WITH_NICKSERV;
	public static boolean VERIFY_ADMIN_NICKS;
	public static boolean DEBUG_MODE;
	public static String PERMISSIONS_DENIED;
	public static String SERVER;
	public static String NOT_ADMIN;
	public static String PASSWORD;
	public static String NICK;
	public static String IDENT;
	public static String REALNAME;
	public static String NOTICE_IDENTIFIER;
	public static String PUBLIC_IDENTIFIER;
	public static String[] CHANS;
	public static String[] ADMINS;
	
	public static void loadConfig() throws ConfigurationException, IOException{
		conf = new PropertiesConfiguration(cnf);
		if (cnf.exists()) {
			System.out.println(cnf.getAbsolutePath());
			conf.load(cnf);
		}else{
			cnf.getParentFile().mkdirs();
			cnf.createNewFile();
			System.out.println(cnf.getAbsolutePath());
			conf.setFile(cnf);
			conf.setProperty("SERVER", "irc.esper.net");
			conf.setProperty("BOT-NICKNAME", "Alphabot");
			conf.setProperty("BOT-IDENT", "Bot");
			conf.setProperty("BOT-REALNAME", "Alphabot");
			conf.setProperty("IDENTIFY-WITH-NICKSERV", true);
			conf.setProperty("NICKSERV-PASS", "password");
			conf.setProperty("CHANNELS","#alphacraft #alphabot");
			conf.setProperty("VERIFY-ADMIN-NICKS", true);
			conf.setProperty("ADMIN-NICKS","zack6849 zack6849|Offline");
			conf.setProperty("PUBLIC-IDENTIFIER", "$");
			conf.setProperty("NOTICE-IDENTIFIER", "|");
			conf.setProperty("PERMISSIONS-DENIED", "Sorry you lack the necessary permissions to perform that action.");
			conf.setProperty("NOT_ADMIN", "Sorry, you are either not on the admins list or are not logged in as an admin.");
			conf.setProperty("DEBUG_MODE", false);
			conf.save();
		}
		IDENTIFY_WITH_NICKSERV = conf.getBoolean("IDENTIFY-WITH-NICKSERV");
		VERIFY_ADMIN_NICKS = conf.getBoolean("VERIFY-ADMIN-NICKS");
		DEBUG_MODE = conf.getBoolean("DEBUG_MODE");
		PERMISSIONS_DENIED = conf.getString("PERMISSIONS-DENIED");
		NOT_ADMIN = conf.getString("NOT_ADMIN");
		PASSWORD = conf.getString("NICKSERV-PASS");
		NICK = conf.getString("BOT-NICKNAME");
		IDENT = conf.getString("BOT-IDENT");
		REALNAME = conf.getString("BOT-REALNAME");
		NOTICE_IDENTIFIER = conf.getString("NOTICE-IDENTIFIER");
		PUBLIC_IDENTIFIER = conf.getString("PUBLIC-IDENTIFIER");
		CHANS = conf.getString("CHANNELS").split(" ");
		ADMINS = conf.getString("ADMIN-NICKS").split(" ");
		SERVER = conf.getString("SERVER");
	}
	public static PropertiesConfiguration getConfig(){
		return conf;
	}
}
