package com.discut.pocket.view;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.SelectItemAdaptor;
import com.discut.pocket.component.SelectItem;
import com.discut.pocket.component.SwitchItem;
import com.discut.pocket.configuration.BiometricConfig;
import com.discut.pocket.configuration.ThemeConfig;
import com.discut.pocket.configuration.ThemeMode;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.view.intf.ISettingView;
import com.discut.pocket.presenter.SettingPresenter;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends BaseActivity<SettingPresenter, ISettingView> implements ISettingView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initConfig();
        initListener();
    }

    @Override
    protected int containerLayout() {
        return R.layout.setting_layout;
    }

    private void initConfig() {

        SelectItem selectTheme = findViewById(R.id.select_theme);
        switch (ThemeConfig.getInstance().getMode()) {
            case AUTO:
                selectTheme.setDetails("跟随系统");
                break;
            case LIGHT:
                selectTheme.setDetails("明亮");
                break;
            default:
                selectTheme.setDetails("黑暗");
                break;
        }

        SwitchItem bio = findViewById(R.id.verification);
        if (BiometricConfig.getInstance().isUse()){
            bio.setDetails("App将在启动时验证您的生物信息");
            bio.setCheck(true);
        }else {
            bio.setDetails("App不会验证生物信息");
            bio.setCheck(false);
        }
    }

    private void initListener() {
        // 导航键监听
        MaterialToolbar topBar = findViewById(R.id.topAppBar);
        topBar.setNavigationOnClickListener(v -> {
            finish();
        });
        // 主题选择监听
        SelectItem selectTheme = findViewById(R.id.select_theme);
        selectTheme.setAdaptor(new SelectItemAdaptor() {
            @Override
            public int getSelectedOption() {
                return ThemeConfig.getInstance().getMode().ordinal();
            }

            @Override
            public String[] getOptions() {
                return new String[]{"跟随系统", "明亮", "黑暗"};
            }
        });
        selectTheme.setOptionListener((v, option, which) -> {
            Log.d(TAG, "initListener: 选择了" + option + which);
            ThemeConfig.getInstance().setMode(ThemeMode.values()[which]);
            switch (option) {
                case "明亮":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "黑暗":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
            }
            initConfig();
        });
        // 生物信息验证监听

        SwitchItem bio = findViewById(R.id.verification);
        bio.setListener(new SwitchItem.CheckedListener() {
            @Override
            public void onCheck(SwitchItem view) {
                BiometricConfig.getInstance().setUse(true);
                initConfig();
            }

            @Override
            public void uncheck(SwitchItem view) {
                BiometricConfig.getInstance().setUse(false);
                initConfig();
            }
        });
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
