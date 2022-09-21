package com.discut.pocket.configuration;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.activity.ComponentActivity;

public class SystemConfig extends BaseConfig {
    private final String NAME = "SystemBoot";
    private String bootPage;

    private SystemConfig() {
    }

    public static SystemConfig getInstance() {
        return Inner.instance;
    }

    public String getBootPage() {
        return bootPage;
    }

    public void setBootPage(String bootPage) {
        this.bootPage = bootPage;
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor edit = getPreferences().edit();
        edit.putString(NAME, bootPage);
        edit.apply();
    }

    @Override
    protected void init(ComponentActivity activity) {
        setPreferences(activity.getSharedPreferences("Theme", Activity.MODE_PRIVATE));
        bootPage = getPreferences().getString(NAME, "最近使用");
        Log.d(TAG, "读取ThemeMode信息为 " + bootPage);
    }

    private static class Inner {
        private static final SystemConfig instance = new SystemConfig();
    }
}
