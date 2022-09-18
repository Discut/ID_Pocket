package com.discut.pocket.view.fragment;

import com.discut.pocket.R;
import com.discut.pocket.mvp.BaseFragment;
import com.google.android.material.transition.MaterialSharedAxis;

public class AccountListFragment extends BaseFragment {
    @Override
    protected void initView() {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
    }

    @Override
    protected int baseLayoutId() {
        return R.layout.account_list_fragment_layout;
    }
}
