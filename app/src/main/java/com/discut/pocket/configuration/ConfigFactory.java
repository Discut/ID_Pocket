package com.discut.pocket.configuration;

import androidx.activity.ComponentActivity;

public class ConfigFactory {
    public static void initConfig(ComponentActivity activity){
        ThemeConfig.getInstance().init(activity);
        BiometricConfig.getInstance().init(activity);
        SystemConfig.getInstance().init(activity);
    }
}
