package kr.ceo.codilook.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceRepository {
    private static PreferenceRepository instance;
    private Application app;

    private PreferenceRepository(Application app) {
        this.app = app;
    }

    public synchronized static PreferenceRepository getInstance(Application app) {
        if (instance == null)
            instance = new PreferenceRepository(app);
        return instance;
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
}
