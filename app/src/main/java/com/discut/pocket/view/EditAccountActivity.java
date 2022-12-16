package com.discut.pocket.view;

import android.os.Bundle;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.TagInputAdaptor;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.component.TagInputView;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.EditAccountPresenter;
import com.discut.pocket.view.intf.IEditAccountView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

import java.util.List;

public class EditAccountActivity extends BaseActivity<EditAccountPresenter, IEditAccountView> implements IEditAccountView {

    private TagInputAdaptor tagInputAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TagInputView tagInputView = findViewById(R.id.edit_account_tag);
        tagInputAdaptor = new TagInputAdaptor();
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
            Account newAccount = new Account();
            String title = ((TextInputLayout) findViewById(R.id.edit_title)).getEditText().getText().toString();
            String account = ((TextInputLayout) findViewById(R.id.edit_account)).getEditText().getText().toString();
            String password = ((TextInputLayout) findViewById(R.id.edit_password)).getEditText().getText().toString();
            String note = ((TextInputLayout) findViewById(R.id.edit_note)).getEditText().getText().toString();

            List<Tag> tag = tagInputAdaptor.getTag();
            Tag[] tags = new Tag[tag.size()];
            for (int i = 0; i < tag.size(); i++) {
                tags[i] = tag.get(i);
            }

            newAccount.setTitle(title);
            newAccount.setAccount(account);
            newAccount.setPassword(password);
            newAccount.setNote(note);
            newAccount.setTags(tags);

            newAccount.setStatus(AccountStatus.NEW);

            AccountModelFactory accountModelFactory = new AccountModelFactory();
            BaseAccountModel baseAccountModel = accountModelFactory.create();
            List<Account> all = baseAccountModel.getAll();
            all.add(newAccount);
            baseAccountModel.update();
            finish();
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