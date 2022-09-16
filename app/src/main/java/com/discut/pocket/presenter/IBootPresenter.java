package com.discut.pocket.presenter;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

/**
 * BootPresenter 接口
 * @version 1.0
 * @author Discut
 */
public interface IBootPresenter {
    /**
     * 开始生物验证
     */
    void bootBiometric(FragmentActivity activity);

    /**
     * 初始化生物信息设备
     * @param context 上下文
     */
    void initBiometricDevice(Context context);
}
