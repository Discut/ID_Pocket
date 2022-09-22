package com.discut.pocket.utils;

import android.app.Activity;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class WindowUtil {
    /**
     *  设置状态栏字体颜色
     *  此api只能控制字体颜色为 黑/白
     *  @param color 这里的颜色是指背景颜色
     */
    public static void setStatusBarTextColor(Activity activity, @ColorInt int color) {
        // 计算颜色亮度
        double luminanceValue = ColorUtils.calculateLuminance(color);
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());
        if (color == Color.TRANSPARENT){
            insetsController.setAppearanceLightStatusBars(true);
        }else {
            insetsController.setAppearanceLightStatusBars(luminanceValue >= 0.5);
        }
    }
}
