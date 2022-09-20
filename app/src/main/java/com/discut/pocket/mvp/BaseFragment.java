package com.discut.pocket.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter<V>, V extends IView> extends Fragment{
    private View contentView;

    protected P presenter;

    protected abstract void initView(View view);

    protected abstract void initData();


    protected abstract @LayoutRes
    int baseLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(baseLayoutId(), container, false);

        presenter = createPresenter();
        presenter.attachView((V)this);
        initData();
        initView(contentView);
        return contentView;
    }

    protected <T extends View> T findViewBy(@IdRes int id) {
        return contentView.findViewById(id);
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
