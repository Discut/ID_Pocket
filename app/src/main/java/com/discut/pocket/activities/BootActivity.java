package com.discut.pocket.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentActivity;

import com.discut.pocket.R;
import com.discut.pocket.utils.BiometricUtil;

/**
 * 启动页activity
 * @author Discut
 * @version 1.0
 */
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


        checkBiometric(this);
        Button btn = findViewById(R.id.fingerprint_btn);
        btn.setOnClickListener(view -> {
            checkBiometric(this);
        });

    }

    /**
     * 验证生物信息
     * @param activity 当前activity
     */
    private void checkBiometric(FragmentActivity activity){
        BiometricUtil biometricUtil = new BiometricUtil();
        biometricUtil.authenticate(activity, new BiometricUtil.BiometricListener() {
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
    }
}