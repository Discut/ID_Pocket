package com.discut.pocket.configuration;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.activity.ComponentActivity;

public class BiometricConfig extends BaseConfig {
    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putBoolean("isUse", use);
        edit.apply();
        isUse = use;
    }

    private boolean isUse = true;

    private BiometricConfig() {
    }

    public static BiometricConfig getInstance() {
        return Inner.instance;
    }

    @Override
    protected void init(ComponentActivity activity) {
        setPreferences(activity.getSharedPreferences("Biometric", Activity.MODE_PRIVATE));
        isUse = getPreferences().getBoolean("isUse", true);
    }

    private static class Inner {
        private static final BiometricConfig instance = new BiometricConfig();
    }
}
