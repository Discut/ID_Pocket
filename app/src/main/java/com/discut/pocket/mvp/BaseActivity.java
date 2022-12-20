package com.discut.pocket.mvp;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.discut.pocket.R;
import com.discut.pocket.utils.WindowUtil;

/**
 * Activity 基类
 *
 * @param <P> Presenter类
 * @param <V> View类
 * @author Discut
 * @version 1.0
 */
public abstract class BaseActivity<P, V extends IView> extends AppCompatActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设定布局
        setContentView(containerLayout());
        // 绑定表示层 presenter

        this.presenter = (P) createPresenter();
        if (this.presenter instanceof BasePresenter)
            ((BasePresenter<V>) this.presenter).attachView((V) this);
        else
            try {
                throw new Exception("You need this class extend BasePresenter class.");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }


        this.init();
        this.initView();
        if (this.isAdaptGestures()) {
            // 适配导航条
            WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

        WindowUtil.setStatusBarTextColor(this, getWindow().getStatusBarColor());

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

    protected abstract @LayoutRes
    int containerLayout();

    protected void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter instanceof BasePresenter)
            ((BasePresenter<V>) this.presenter).detachView();
        else
            try {
                throw new Exception("You need this class extend BasePresenter class.");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * 获取presenter
     *
     * @return 返回presenter
     */
    protected abstract BasePresenter<V> createPresenter();

    /**
     * 初始化
     */
    protected void init() {
    }

    /**
     * 用于控制当前activity是否适配小白条（导航条）
     * 复写即可更改
     *
     * @return 是否适配
     */
    protected boolean isAdaptGestures() {
        return true;
    }

}
