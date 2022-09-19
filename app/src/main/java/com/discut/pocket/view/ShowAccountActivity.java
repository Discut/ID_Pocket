package com.discut.pocket.view;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.discut.pocket.R;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.ShowAccountPresenter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class ShowAccountActivity extends BaseActivity<ShowAccountPresenter, IShowAccountView> implements IShowAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViewById(R.id.extra).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                int top = WindowInsetsCompat.toWindowInsetsCompat(insets, v).getInsets(WindowInsetsCompat.Type.statusBars()).top;
                v.setPaddingRelative(v.getPaddingStart(), top, v.getPaddingEnd(), v.getPaddingBottom());
                return insets;
            }
        });


    }

    @Override
    protected void initView() {
        // Attach a callback used to receive the shared elements from Activity A to be
        // used by the container transform transition.
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        // Set this Activity’s enter and return transition to a MaterialContainerTransform


        MaterialContainerTransform transform3 = new MaterialContainerTransform();
        transform3.addTarget(R.id.container).setDuration(300);
        getWindow().setSharedElementEnterTransition(transform3);

        MaterialContainerTransform transform4 = new MaterialContainerTransform();
        transform4.addTarget(R.id.container).setDuration(200);
        getWindow().setSharedElementReturnTransition(transform4);


        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(v -> onBackPressed());

    }

    @Override
    protected int containerLayout() {
        return R.layout.show_account_layout;
    }

    @Override
    protected ShowAccountPresenter createPresenter() {
        return new ShowAccountPresenter();
    }

    @Override
    public void showMsg(String msg) {

    }
}