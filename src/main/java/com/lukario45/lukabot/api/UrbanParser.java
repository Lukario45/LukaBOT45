/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukario45.lukabot.api;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author Kevin
 */
public class UrbanParser {

    public static String search(String term, String ent) throws IOException {

        int entry;
        entry = Integer.parseInt(ent) - 1;
        if (entry == -1) {
            entry = 0;
        }

        Document doc = Jsoup.connect("http://www.api.urbandictionary.com/define.php?term=" + term).get();
        Elements words = doc.select("div.word");
        Elements definition = doc.select("div.meaning");
        Elements example = doc.select("div.example");
        String word = words.get(entry).text();
        String meaning = definition.get(entry).text();
        String ex = example.get(entry).text();
        return entry + ":" + word + ":" + meaning + ":" + ex;
    }
}
