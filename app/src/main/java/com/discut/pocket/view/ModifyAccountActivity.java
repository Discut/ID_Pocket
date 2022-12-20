package com.discut.pocket.view;

import android.os.Bundle;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.TagInputAdaptor;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.component.TagInputView;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.presenter.ModifyAccountPresenter;
import com.discut.pocket.presenter.intf.IModifyAccountPresenter;
import com.discut.pocket.view.intf.IModifyAccountView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class ModifyAccountActivity extends BaseActivity<IModifyAccountPresenter, IModifyAccountView> implements IModifyAccountView {

    private Account account;

    private TagInputAdaptor tagInputAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initListener() {
        // 设置回退键
        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        // save按钮 点击事件
        ExtendedFloatingActionButton fab = findViewById(R.id.floating_action_button_save);
        fab.setOnClickListener(v -> {
            String title = ((TextInputLayout) findViewById(R.id.edit_title)).getEditText().getText().toString();
            String account = ((TextInputLayout) findViewById(R.id.edit_account)).getEditText().getText().toString();
            String password = ((TextInputLayout) findViewById(R.id.edit_password)).getEditText().getText().toString();
            String note = ((TextInputLayout) findViewById(R.id.edit_note)).getEditText().getText().toString();

            List<Tag> tag = tagInputAdaptor.getTag();
            Tag[] tags = new Tag[tag.size()];
            for (int i = 0; i < tag.size(); i++) {
                Tag newTag = tag.get(i);
                newTag.setAccountId(Integer.parseInt(this.account.getId()));
                tags[i] = newTag;
            }

            this.account.setTitle(title);
            this.account.setAccount(account);
            this.account.setPassword(password);
            this.account.setNote(note);
            this.account.setTags(tags);

            this.account.setStatus(AccountStatus.MODIFIED);

            presenter.update(this.account);

            finish();
        });
    }

    @Override
    protected void init() {
        account = (Account) getIntent().getSerializableExtra("account");
    }

    @Override
    protected void initView() {
        ((TextInputLayout) findViewById(R.id.edit_title)).getEditText().setText(account.getTitle());
        ((TextInputLayout) findViewById(R.id.edit_account)).getEditText().setText(account.getAccount());
        ((TextInputLayout) findViewById(R.id.edit_password)).getEditText().setText(account.getPassword());
        ((TextInputLayout) findViewById(R.id.edit_note)).getEditText().setText(account.getNote());

        TagInputView tagInputView = findViewById(R.id.edit_account_tag);
        tagInputAdaptor = new TagInputAdaptor(account.getTags());
        tagInputAdaptor.onCreateView(tagInputView);
        tagInputView.setAdaptor(tagInputAdaptor);
    }

    @Override
    protected int containerLayout() {
        return R.layout.activity_modify_account;
    }

    @Override
    protected BasePresenter<IModifyAccountView> createPresenter() {
        return new ModifyAccountPresenter();
    }

    @Override
    public void showMsg(String msg) {

    }
}