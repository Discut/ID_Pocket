package com.discut.pocket.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.discut.pocket.R;
import com.discut.pocket.utils.BiometricUtil;

public class BootActivity extends AppCompatActivity {

/*    @Override
    protected void onStart() {
        super.onStart();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot_layout);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);


        Button btn = findViewById(R.id.fingerprint_btn);
        BootActivity bootActivity = this;
        btn.setOnClickListener(view -> {
            BiometricUtil biometricUtil = new BiometricUtil();
            biometricUtil.authenticate(bootActivity, new BiometricUtil.BiometricListener() {
                @Override
                public void failed() {

                }

                @Override
                public void success() {
                    Log.d("TAG", "success: 验证成功的消息");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

            });

        });
    }
}