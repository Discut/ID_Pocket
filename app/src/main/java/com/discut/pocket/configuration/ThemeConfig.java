package com.discut.pocket.configuration;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.activity.ComponentActivity;

public class ThemeConfig extends BaseConfig{
    private ThemeMode mode;
    private final String NAME = "ThemeMode";
    private ThemeConfig() {
    }

    public static ThemeConfig getInstance() {
        return InnerClass.instance;
    }

    public ThemeMode getMode() {
        return mode;
    }

    public void setMode(ThemeMode mode) {
        this.mode = mode;
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = getPreferences().edit();
        edit.putString(NAME, mode.name());
        edit.apply();
    }

    @Override
    protected void init(ComponentActivity activity) {
        setPreferences(activity.getSharedPreferences("Theme", Activity.MODE_PRIVATE));
        mode = ThemeMode.valueOf(getPreferences().getString(NAME, "AUTO"));
        Log.d(TAG, "读取ThemeMode信息为 "+mode);
    }

    private static class InnerClass {
        private static final ThemeConfig instance = new ThemeConfig();
    }
}
