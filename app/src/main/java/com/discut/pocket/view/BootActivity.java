package com.discut.pocket.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.discut.pocket.R;
import com.discut.pocket.configuration.BiometricConfig;
import com.discut.pocket.configuration.ConfigFactory;
import com.discut.pocket.configuration.ThemeConfig;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.BootPresenter;

/**
 * 启动页activity
 *
 * @author Discut
 * @version 1.0
 */
public class BootActivity extends BaseActivity<BootPresenter, IBootView> implements IBootView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化系统
        initSystem();
        if (!BiometricConfig.getInstance().isUse()) {
            navigateTo(MainActivity.class);
            return;
        }
        // 验证硬件
        this.presenter.initBiometricDevice(getApplicationContext());

        this.presenter.bootBiometric(this);
        Button btn = findViewById(R.id.fingerprint_btn);
        btn.setOnClickListener(view -> {
            this.presenter.bootBiometric(this);
        });

    }

    @Override
    protected int containerLayout() {
        return R.layout.boot_layout;
    }

    private void initSystem() {
        ConfigFactory.initConfig(this);
        switch (ThemeConfig.getInstance().getMode()) {
            case LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    @Override
    protected BootPresenter createPresenter() {
        return new BootPresenter();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateTo(Class<?> clazz) {
        if (!AppCompatActivity.class.isAssignableFrom(clazz))
            return;
        startActivity(new Intent(getApplicationContext(), clazz));
        finish();
    }
}