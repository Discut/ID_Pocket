package com.discut.pocket.view;

import android.content.Intent;
import android.os.Bundle;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.TagInputAdaptor;
import com.discut.pocket.component.TagInputView;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.EditAccountPresenter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class EditAccountActivity extends BaseActivity<EditAccountPresenter, IEditAccountView> implements IEditAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TagInputView tagInputView = findViewById(R.id.edit_account_tag);
        TagInputAdaptor tagInputAdaptor = new TagInputAdaptor(new String[]{"123", "456"});
        tagInputAdaptor.onCreateView(tagInputView);
        tagInputView.setAdaptor(tagInputAdaptor);
    }

    @Override
    protected void initView() {
        // Attach a callback used to receive the shared elements from Activity A to be
        // used by the container transform transition.
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        // Set this Activity’s enter and return transition to a MaterialContainerTransform


        MaterialContainerTransform transform3 = new MaterialContainerTransform();
        transform3.addTarget(R.id.edit_account_layout_content).setDuration(300);
        getWindow().setSharedElementEnterTransition(transform3);

        MaterialContainerTransform transform4 = new MaterialContainerTransform();
        transform4.addTarget(R.id.edit_account_layout_content).setDuration(200);
        getWindow().setSharedElementReturnTransition(transform4);

        initListener();
    }

    private void initListener() {
        // 设置回退键
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        // save按钮 点击事件
        ExtendedFloatingActionButton fab = findViewById(R.id.floating_action_button_save);
        fab.setOnClickListener(v -> {

        });
    }

    @Override
    protected int containerLayout() {
        return R.layout.edit_account_layout;
    }

    @Override
    protected EditAccountPresenter createPresenter() {
        return new EditAccountPresenter();
    }

    @Override
    public void showMsg(String msg) {

    }
}