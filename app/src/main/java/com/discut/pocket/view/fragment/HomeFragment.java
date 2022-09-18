package com.discut.pocket.view.fragment;

import com.discut.pocket.R;
import com.discut.pocket.mvp.BaseFragment;
import com.google.android.material.transition.MaterialSharedAxis;

public class HomeFragment extends BaseFragment{
    @Override
    protected void initView() {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    }

    @Override
    protected int baseLayoutId() {
        return R.layout.home_fragment_layout;
    }

}
