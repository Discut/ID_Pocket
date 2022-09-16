package com.discut.pocket.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.discut.pocket.R;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.BootPresenter;

/**
 * 启动页activity
 * @author Discut
 * @version 1.0
 */
public class BootActivity extends BaseActivity<BootPresenter, IBootView> implements IBootView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot_layout);

        this.presenter.initBiometricDevice(getApplicationContext());

        this.presenter.bootBiometric(this);
        Button btn = findViewById(R.id.fingerprint_btn);
        btn.setOnClickListener(view -> {
            this.presenter.bootBiometric(this);
        });

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