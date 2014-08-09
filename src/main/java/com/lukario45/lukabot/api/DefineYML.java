package com.lukario45.lukabot.api;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class DefineYML {

    private static PropertiesConfiguration define;
    public static File def = new File("C:\\Users\\Kevin\\.LukaBOT45\\define.yml");

    public static void loadDefine() throws ConfigurationException, IOException {
        define = new PropertiesConfiguration(def);
        if (def.exists()) {
            System.out.println(def.getAbsolutePath());
            define.load(def);
        } else {
            def.getParentFile().mkdirs();
            def.createNewFile();
            System.out.println(def.getAbsolutePath());
            define.setFile(def);
            define.load(def);

        }
    }

    public static void addDefine(String args1, String args2) {

        try {
            define.setProperty(args1, args2);
            define.save();
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void delDefine(String word) {

        define.setProperty(word, null);
        try {
            define.save();
        } catch (ConfigurationException ex) {
            Logger.getLogger(DefineYML.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    static String defin;

    public static void getDefine(String word) {
        defin = define.getString(word);



    }

    public static boolean ifExists(String word) {
        return define.getString(word) != null;

    }
}
