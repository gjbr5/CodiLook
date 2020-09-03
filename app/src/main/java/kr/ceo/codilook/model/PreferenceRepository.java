package kr.ceo.codilook.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceRepository {
    private Application app;

    private PreferenceRepository() {
    }

    public static PreferenceRepository getInstance(Application app) {
        SingletonHolder.instance.init(app);
        return SingletonHolder.instance;
    }

    private void init(Application app) {
        this.app = app;
    }

    public boolean getAutoLogin() {
        SharedPreferences sp = app.getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        return sp.getBoolean("autoLogin", false);
    }

    public void setAutoLogin(boolean autoLogin) {
        SharedPreferences sp = app.getSharedPreferences("AutoLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autoLogin", autoLogin);
        editor.apply();
    }

    private static class SingletonHolder {
        private static final PreferenceRepository instance = new PreferenceRepository();
    }
}
