package com.discut.pocket.utils;

import static androidx.biometric.BiometricPrompt.ERROR_LOCKOUT;
import static androidx.biometric.BiometricPrompt.ERROR_LOCKOUT_PERMANENT;
import static androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON;
import static androidx.biometric.BiometricPrompt.ERROR_NO_DEVICE_CREDENTIAL;
import static androidx.biometric.BiometricPrompt.ERROR_NO_SPACE;
import static androidx.biometric.BiometricPrompt.ERROR_TIMEOUT;
import static androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

/**
 * 生物验证类
 * @author Discut
 * @version 1.0
 */
public class BiometricUtil {

    public abstract static class BiometricListener {
        /**
         * 验证取消
         */
        public void cancel() {
        }

        ;

        /**
         * 错误次数超过五次
         */
        public void lockout() {
        }

        ;

        /**
         * 失败次数太多
         */
        public void lockoutPermanent() {
        }

        ;

        /**
         * 没有存储空间
         */
        public void noSpace() {
        }

        ;

        /**
         * 验证超时
         */
        public void timeout() {
        }

        ;

        /**
         * 验证失败
         */
        public abstract void failed();

        /**
         * 验证成功
         */
        public abstract void success();
    }

    private BiometricUtil.BiometricListener listener;


    /**
     * BiometricUtil constructor
     *
     * @param listener 事件监听器
     */
    public BiometricUtil(BiometricUtil.BiometricListener listener) {
        this.listener = listener;
    }

    public BiometricUtil() {
    }

    /**
     * 设置事件监听器
     *
     * @param listener 事件监听器
     */
    public void setListener(BiometricListener listener) {
        this.listener = listener;
    }

    /**
     * 检查指纹硬件是否可用
     *
     * @param context
     * @return
     */
    public int checkFingerprintAvailable(Context context) {
        BiometricManager manager = BiometricManager.from(context);
        return manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);
    }

    /**
     * 开始验证
     *
     * @param activity activity对象
     */
    public void authenticate(FragmentActivity activity) {
        BiometricPrompt.AuthenticationCallback callBack = new BiometricPrompt.AuthenticationCallback() {
            /**
             * 验证过程中发生了错误
             * @param errorCode
             * @param errString
             */
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                switch (errorCode) {
                    case ERROR_USER_CANCELED:
                        Log.d("cyx", "取消了指纹识别");
                        listener.cancel();
                        break;
                    case ERROR_LOCKOUT:
                        Log.d("cyx", "失败5次，已锁定，请30秒后在试");
                        listener.lockout();
                        break;
                    case ERROR_LOCKOUT_PERMANENT:
                        Log.d("cyx", "失败次数太多，指纹验证已锁定，请改用密码，图案等方式解锁");
                        listener.lockoutPermanent();
                    case ERROR_NEGATIVE_BUTTON:
                        Log.d("cyx", "点击了negative button");
                        break;
                    case ERROR_NO_DEVICE_CREDENTIAL:
                        Log.d("cyx", "尚未设置密码，图案等解锁方式");
                        break;
                    case ERROR_NO_SPACE:
                        Log.d("cyx", "可用空间不足");
                        listener.noSpace();
                        break;
                    case ERROR_TIMEOUT:
                        Log.d("cyx", "验证超时");
                        listener.timeout();
                        break;
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                Log.d("cyx", "验证成功");
                listener.success();
            }

            /**
             * 验证失败
             * @param
             */
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.d("cyx", "验证失败，请重试");
                listener.failed();
            }
        };
        if (null == this.listener)
            return;
        BiometricPrompt.PromptInfo promptInfo = createUi();
        BiometricPrompt prompt = new BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), callBack);
        prompt.authenticate(promptInfo);
    }

    /**
     * 开始验证
     *
     * @param activity
     * @param listener 监视器
     */
    public void authenticate(FragmentActivity activity, BiometricUtil.BiometricListener listener) {
        if (null != listener)
            this.listener = listener;
        authenticate(activity);
    }

    private BiometricPrompt.PromptInfo createUi() {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("验证生物信息")
                .setSubtitle("已检测到此设备已开启生物信息验证")
                .setNegativeButtonText("取消")
                .build();
    }
}
