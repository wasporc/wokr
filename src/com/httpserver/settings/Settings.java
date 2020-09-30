package com.httpserver.settings;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.prefs.Preferences;

final public class Settings {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    private static final File FILE_SETTINGS = new File("saves/settings.ini");

    public static final HashMap<String,String> data = new HashMap<>();

    public static void init() {
        try {
            Ini ini = new Ini(FILE_SETTINGS);
            Preferences preferences = new IniPreferences(ini);
            data.put("HOST",preferences.node("Settings").get("HOST", null));
            data.put("USER",preferences.node("Settings").get("USER", null));
            data.put("PASS",preferences.node("Settings").get("PASS", null));
            data.put("TABLE",preferences.node("Settings").get("TABLE", null));
            data.put("PORTS",preferences.node("Settings").get("PORTS", null));
            data.put("PORTSQL",preferences.node("Settings").get("PORTSQL", null));
            //System.out.println(host + user + pass + table);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
