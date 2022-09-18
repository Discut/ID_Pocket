package com.discut.pocket.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class ColorTransform {
    public static ColorStateList from(@NonNull Resources res, @ColorRes int id) {
        return ColorStateList.valueOf(ResourcesCompat.getColor(res, id, null));
    }

    public static ColorStateList from(@NonNull String color) {
        if (color.isEmpty())
            color = "#FFFFFF";
        return ColorStateList.valueOf(Color.parseColor(color));
    }
}
