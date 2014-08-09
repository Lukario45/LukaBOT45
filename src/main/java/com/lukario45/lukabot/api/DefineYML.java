package com.lukario45.lukabot.api;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class DefineYML {
    public static String defin;
    private static PropertiesConfiguration define;
    public static void load(){
        try{
            define = new PropertiesConfiguration(new File(System.getProperty("user.home") + "/.LukaBOT45/define.yml"));
            System.out.println(define.getFile().getAbsolutePath());
            if (define.getFile().exists()) {
                define.load(define.getFile());
            } else {
                if(!define.getFile().getParentFile().mkdirs() || define.getFile().createNewFile()){
                    Logger.getLogger(DefineYML.class.getName()).log(Level.WARNING, "Couldn't create definition file, are you sure you have write access?");
                }
                define.load(define.getFile());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void addDefinition(String word, String definition) {
        if(define.containsKey(word)){
            define.clearProperty(word);
        }
        try {
            define.setProperty(word, definition);
            define.save();
            define.refresh();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void delDefinition(String word) {
        define.clearProperty(word);
        try {
            define.save();
            define.refresh();
        } catch (ConfigurationException ex) {
            Logger.getLogger(DefineYML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getDefinition(String word)
    {
        if(hasDefinition(word)){
            return define.getString(word);
        }
        return null;
    }


    public static boolean hasDefinition(String word) {
        return define.containsKey(word);
    }

    public static void reload(){
        try {
            define.save();
            define.refresh();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
