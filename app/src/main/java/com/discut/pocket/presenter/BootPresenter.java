package com.discut.pocket.presenter;

import android.content.Context;
import android.util.Log;

import androidx.biometric.BiometricManager;
import androidx.fragment.app.FragmentActivity;

import com.discut.pocket.view.MainActivity;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.utils.BiometricUtil;
import com.discut.pocket.view.IBootView;

/**
 * boot的persenter
 * @version 1.0
 * @author Discut
 */
public class BootPresenter extends BasePresenter<IBootView> implements IBootPresenter {
    private final BiometricUtil biometric = new BiometricUtil();
    /**
     * biometric硬件状态
     */
    private int biometricDeviceStatus;

    @Override
    public void bootBiometric(FragmentActivity activity) {
        if (biometricDeviceStatus != BiometricManager.BIOMETRIC_SUCCESS)
            return;
        this.biometric.authenticate(activity, new BiometricUtil.BiometricListener() {
            @Override
            public void failed() {
                reference.get().showMsg("验证失败");
            }

            @Override
            public void success() {
                Log.d("TAG", "success: 验证成功的消息");
                IBootView view = reference.get();
                view.navigateTo(MainActivity.class);
                view.showMsg("验证成功");
            }
        });
    }

    @Override
    public void initBiometricDevice(Context context) {
        this.biometricDeviceStatus = this.biometric.checkFingerprintAvailable(context);
        switch (this.biometricDeviceStatus) {
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
            default:
                reference.get().showMsg("未能成功初始化生物验证设备");
                IBootView view = reference.get();
                view.navigateTo(MainActivity.class);
                break;
            case BiometricManager.BIOMETRIC_SUCCESS:

                break;
        }
    }
}
