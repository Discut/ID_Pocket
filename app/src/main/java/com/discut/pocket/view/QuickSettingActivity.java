package com.discut.pocket.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.discut.pocket.ModalBottomSheet;
import com.discut.pocket.R;

import java.lang.reflect.Method;

public class QuickSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_setting);

        ModalBottomSheet modalBottomSheet = new ModalBottomSheet();

        modalBottomSheet.show(getSupportFragmentManager(), ModalBottomSheet.TAG);
    }
}