package com.kosherbacon.mmcfe_ng;


import android.content.Context;
import android.preference.PreferenceManager;

import java.io.*;
import java.util.ArrayList;

public class Preferences {
    public static PreferencesData servers = new PreferencesData();
    public static final String preferenceName = "ServerDataWithWorkers";

    public static void savePreferences(String preferences, Context mContext){
        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(preferenceName, preferences).commit();
    }

    public static String loadPreferences(String preferences, Context mContext){
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(preferenceName, preferences);
    }

    public static ArrayList<ServerItem> getServerItems() {
        ArrayList<ServerItem> items = new ArrayList<ServerItem>();
        for (ServerEntry entry : Preferences.servers) {
            items.add(new ServerItem(entry.getUrl(), entry.getApiKey(), entry.getUserID(), false));
        }
        items.add(new ServerItem("", "", "", true));
        return items;
    }

    /** Read the object from Base64 string. */
    public static PreferencesData fromString(String s ) throws IOException, ClassNotFoundException {
        byte [] data = Base64Coder.decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o  = ois.readObject();
        ois.close();
        if (o instanceof PreferencesData) {
            return (PreferencesData) o;
        }
        return null;
    }

    /** Write the object to a Base64 string. */
    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }
}