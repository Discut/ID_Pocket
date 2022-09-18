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

public abstract class BaseFragment extends Fragment {
    private View contentView;

    protected abstract void initView();

    protected abstract @LayoutRes
    int baseLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(baseLayoutId(), container, false);
        initView();
        return contentView;
    }

    protected <T extends View> T findViewBy(@IdRes int id) {
        return contentView.findViewById(id);
    }
}
