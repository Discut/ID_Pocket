package com.discut.pocket.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

/**
 * Activity 基类
 * @version 1.0
 * @author Discut
 * @param <P> Presenter类
 * @param <V> View类
 */
public abstract class BaseActivity <P extends BasePresenter, V extends IView> extends AppCompatActivity {
    protected P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 绑定表示层 presenter
        this.presenter = createPresenter();
        this.presenter.attachView((V) this);
        this.init();
        if (this.isAdaptGestures()){
            // 适配导航条
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
    }

    /**
     * 获取presenter
     * @return 返回presenter
     */
    protected abstract P createPresenter();

    /**
     * 初始化
     */
    protected void init(){}

    /**
     * 用于控制当前activity是否适配小白条（导航条）
     * 复写即可更改
     * @return 是否适配
     */
    protected boolean isAdaptGestures(){return true;}
}
