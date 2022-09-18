package com.discut.pocket.configuration;

import android.content.SharedPreferences;

import androidx.activity.ComponentActivity;

public abstract class BaseConfig {

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    private SharedPreferences preferences;

    protected abstract void init(ComponentActivity activity);
}
