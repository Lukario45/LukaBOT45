/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lukario45.lukabot.api;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.UserLevel;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author zack6849(zcraig29@gmail.com)
 */
public class Utils {



    public static String htmlFormat(String s) {
        return s.replaceAll("<b>", "").replace("</b>", "").replace("&#39;", "'").replaceAll("&quot;", "'").replaceAll("   ", " ").replaceAll("&amp;", "&");
    }
    /* public static String google(String s) {
     StringBuilder sb = new StringBuilder();
     GoogleSearchQueryFactory factory = GoogleSearchQueryFactory.newInstance("AIzaSyD3zNgBYmpBClYiJxdCM6tmehjNEQLofis");
     WebSearchQuery query = factory.newWebSearchQuery();
     PagedList<WebResult> response = query.withQuery(s).list();
     if (response.isEmpty()) {
     return "No results found for query " + s;
     } else {
     String link = response.get(0).getUrl();
     String title = Utils.htmlFormat(response.get(0).getTitle());
     String c = Utils.htmlFormat(response.get(0).getContent());
     String r = String.format("%s - %s (URL : %s )", title, c, link);
     return r;
     }
     }**/

    public static String removeBrackets(String s) {
        return s.replace("[", "").replace("]", "");
    }

    /**
     * @param user the Minecraft username to check
     * @return returns a boolean depending upon if he username has paid or not
     * (note: accounts that do not exists return false too)
     */
    public static boolean checkAccount(String user) {
        boolean paid = false;
        try {
            URL url = new URL("https://minecraft.net/haspaid.jsp?user=" + user);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = in.readLine();
            in.close();
            if (str != null) {
                paid = Boolean.valueOf(str);
            }
        } catch (java.io.IOException e1) {
        }
        return paid;
    }

    /*
     * @return a string with the status.
     */
    public static boolean checkPerms(MessageEvent e, Config c){
        User user = e.getUser();
      if (c.getAdmins().contains(user.getNick())){
          if (user.isVerified()){
              return true;
          } else {
              return false;
          }

      }else {
          return false;
      }
    }
    public static String checkMojangServers() {
        String returns = null;
        try {
            URL url;
            url = new URL("http://status.mojang.com/check");
            BufferedReader re = new BufferedReader(new InputStreamReader(url.openStream()));
            String st;
            while ((st = re.readLine()) != null) {
                String a = st.replace("red", Colors.RED + "Offline" + Colors.NORMAL).replace("green", Colors.GREEN + "Online" + Colors.NORMAL).replace("[", "").replace("]", "");
                String b = a.replace("{", "").replace("}", "").replace(":", " is currently ").replace("\"", "").replaceAll(",", ", ");
                returns = b;
            }
            re.close();
        } catch (IOException E) {
            if (E.getMessage().contains("503")) {
                returns = "The minecraft status server is temporarily unavailable, please try again later";
            }
            if (E.getMessage().contains("404")) {
                returns = "Uhoh, it would appear as if the haspaid page has been removed or relocated >_>";
            }
        }
        return returns;
    }

    /**
     *
     * @param longUrl the URL to shorten
     * @return the shortened URL
     */
    public static String shortenUrl(String longUrl) {
        String shortened = null;
        try {
            URL u;
            u = new URL("http://is.gd/create.php?format=simple&url=" + longUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
            shortened = br.readLine();
            br.close();
        } catch (Exception e) {
        }
        return shortened;
    }

    public static String checkServerStatus(InetAddress i) {
        return checkServerStatus(i, 25565);
    }

    public static String checkServerStatus(InetAddress i, int port) {
        String returns;
        try {
            Socket s = new Socket(i, port);
            DataInputStream SS_BF = new DataInputStream(s.getInputStream());
            DataOutputStream d = new DataOutputStream(s.getOutputStream());
            d.write(0xFE);
            SS_BF.readByte();
            short length = SS_BF.readShort();
            StringBuilder sb = new StringBuilder();
            for (int in = 0; in < length; in++) {
                char ch = SS_BF.readChar();
                sb.append(ch);
            }
            String all = sb.toString().trim();
            String[] args1 = all.split("�");
            returns = "MOTD: " + args1[0] + "   players: [" + args1[0] + "/" + args1[0] + "]";
        } catch (UnknownHostException e1) {
            returns = "the host you specified is unknown. check your settings.";
        } catch (IOException e1) {
            returns = "sorry, we couldn't reach this server, make sure that the server is up and has query enabled.";
        }

        return returns;
    }

    public static String getWebpageTitle(String s) {
        String title = null;
        try {
            URL u = new URL(s);
            URLConnection urlc = u.openConnection();
            BufferedReader dis = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String html = "", tmp = "";
            while ((tmp = dis.readLine()) != null) {
                html += " " + tmp;
            }
            dis.close();
            html = html.replace("\\s+", " ");
            Pattern p = Pattern.compile("<title>.+</title>");
            Matcher m = p.matcher(html);
            if (m.find()) {
                title = m.group().replaceAll("<title>", "").replaceAll("</title>", "").replaceAll("\t", "").replaceAll("&trade;", "�").replaceAll("&gt;", ">").replace("\n", "").replaceAll("\\s+", " ");
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            return e.getCause().toString();
        }
        return title;
    }

    public static boolean isUrl(String s) {
        String url_regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(url_regex);
        Matcher m = p.matcher(s);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getRank(Channel chan, User user) {
        ArrayList<Integer> levels = new ArrayList<>();
        int highest = -1;
        for (UserLevel level : user.getUserLevels(chan)) {
            levels.add(level.ordinal());
        }
        for (int level : levels) {
            if (highest < level) {
                highest = level;
            }
        }
        return highest;
    }
}
