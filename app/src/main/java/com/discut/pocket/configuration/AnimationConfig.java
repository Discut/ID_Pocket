package com.discut.pocket.configuration;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.activity.ComponentActivity;

public class AnimationConfig extends BaseConfig {
    private final String NAME = "AnimationConfig";
    private boolean enableAnimation;

    private AnimationConfig() {
    }

    public static AnimationConfig getInstance() {
        return Inner.instance;
    }

    public boolean isEnableAnimation() {
        return enableAnimation;
    }

    public void setEnableAnimation(boolean enableAnimation) {
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putBoolean("enableAnimation", enableAnimation);
        edit.apply();
        this.enableAnimation = enableAnimation;
    }

    @Override
    protected void init(ComponentActivity activity) {
        setPreferences(activity.getSharedPreferences(NAME, Activity.MODE_PRIVATE));
        enableAnimation = getPreferences().getBoolean("enableAnimation", false);
    }

    private static class Inner {
        private static final AnimationConfig instance = new AnimationConfig();
    }
}
