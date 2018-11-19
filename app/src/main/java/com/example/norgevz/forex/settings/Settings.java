package com.example.norgevz.forex.settings;

import com.example.norgevz.forex.global.MyApplication;

public final class Settings {

    private static MyApplication state = MyApplication.getInstance();

    public static String getBaseEndpoint() {
        return state.getSharedPref().getString("entry_point", "");
    }

    public static void setBaseEndpoint(String _endpoint) {
        state.getEditor().putString("entry_point", _endpoint);
        state.getEditor().commit();
    }

    public static String getApiKey() {
        return state.getSharedPref().getString("app_key", "");
    }

    public static void setApiKey(String _key) {
        state.getEditor().putString("app_key", _key);
        state.getEditor().commit();
    }

    public static boolean containsSettings(String name) {
        return state.getSharedPref().contains(name);
    }

    public static String getSettings(String name) throws Exception {

        if (containsSettings(name))
            return state.getSharedPref().getString(name, "");
        else {
            throw new Exception("System do not contains current settings!");
        }
    }

    public static void addSetting(String name, String value) {
        state.getEditor().putString(name, value);
        state.getEditor().commit();
    }

    public static void removeSettings(String name){
        if(containsSettings(name)) {
            state.getEditor().remove(name);
            state.getEditor().commit();
        }
    }

}
