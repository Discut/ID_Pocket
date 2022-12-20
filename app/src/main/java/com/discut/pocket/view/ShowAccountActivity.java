package com.discut.pocket.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.WindowInsetsCompat;

import com.discut.pocket.R;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.component.InfoCard;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.presenter.ShowAccountPresenter;
import com.discut.pocket.presenter.intf.IShowAccountPresenter;
import com.discut.pocket.utils.ColorTransform;
import com.discut.pocket.view.intf.IShowAccountView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class ShowAccountActivity extends BaseActivity<IShowAccountPresenter, IShowAccountView> implements IShowAccountView {
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViewById(R.id.extra).setOnApplyWindowInsetsListener((v, insets) -> {
            int top = WindowInsetsCompat.toWindowInsetsCompat(insets, v).getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPaddingRelative(v.getPaddingStart(), top, v.getPaddingEnd(), v.getPaddingBottom());
            return insets;
        });
    }

    @Override
    protected void init() {
        super.init();
        account = (Account) getIntent().getSerializableExtra("account");
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

        // 设定数据

        ((TextView) findViewById(R.id.account_title_card)).setText(account.getTitle());
        ((InfoCard) findViewById(R.id.account_account_card)).setDetails(account.getAccount());
        ((InfoCard) findViewById(R.id.account_password_card)).setDetails(account.getPassword());
        ((InfoCard) findViewById(R.id.account_note_card)).setDetails(account.getNote());

        ChipGroup chipGroup = findViewById(R.id.account_tags_card);
        for (Tag tag :
                account.getTags()) {
            @SuppressLint("ResourceType") Chip newChip =
                    (Chip) LayoutInflater.from(this).inflate(R.xml.chip_item, chipGroup, false);
            newChip.setText(tag.getName());
            newChip.setTextColor(Color.WHITE);
            newChip.setClickable(false);
            int color;
            if (null == tag.getColor() || tag.getColor().equals("")) {
                color = ResourcesCompat.getColor(getResources(), R.color.chip_background_color, null);
            } else {
                color = ColorTransform.from(tag.getColor());
            }
            newChip.setChipBackgroundColor(ColorStateList.valueOf(color));
            chipGroup.addView(newChip);
        }

        initListener();
    }

    private void initListener() {
        BottomAppBar appBar = findViewById(R.id.bottomAppBar);
        appBar.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_share: {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, "账号：" + account.getAccount() + "\n密码：" + account.getPassword());
                            intent.setType("text/plain");
                            startActivity(Intent.createChooser(intent, "发送到..."));
                            break;
                        }
                        case R.id.menu_modify: {
                            // TODO 修改account按钮点击事件
                            break;
                        }
                        case R.id.menu_delete: {
                            presenter.deleteAccount(account);
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                            materialAlertDialogBuilder.setTitle("确定删除吗？").setPositiveButton("确认", (dialog, which) -> {
                                presenter.deleteAccount(account);
                                finish();
                            }).setNeutralButton("取消", (dialog, which) -> {

                            }).show();
                            break;
                        }

                    }
                    return false;
                }
        );
    }

    @Override
    protected int containerLayout() {
        return R.layout.show_account_layout;
    }

    @Override
    protected BasePresenter<IShowAccountView> createPresenter() {
        return new ShowAccountPresenter();
    }

    @Override
    public void showMsg(String msg) {

    }
}