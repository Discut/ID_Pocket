package com.discut.pocket.view.fragment;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.RecyclerAdaptor;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.configuration.AnimationConfig;
import com.discut.pocket.mvp.BaseFragment;
import com.discut.pocket.presenter.AccountListPresenter;
import com.discut.pocket.utils.ColorTransform;
import com.discut.pocket.view.MainActivity;
import com.discut.pocket.view.ShowAccountActivity;
import com.discut.pocket.view.intf.IAccountListView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.List;

public class AccountListFragment extends BaseFragment<AccountListPresenter, IAccountListView> implements IAccountListView {
    private List<Account> accountList;

    @Override
    protected void initView(View view) {

        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

        // 滑动事件
        NestedScrollView listView = view.findViewById(R.id.account_list);
        listView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                if (scrollY > oldScrollY) {
                    activity.hideBottomBar();
                } else {
                    activity.showBottomBar();
                }
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.update(null);
    }

    @Override
    protected int baseLayoutId() {
        return R.layout.account_list_fragment_layout;
    }

    @Override
    protected AccountListPresenter createPresenter() {
        return new AccountListPresenter();
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void updateAccountList(@NonNull List<Account> accounts) {
        Log.d(TAG, "updateAccountList: 更新item");
        RecyclerAdaptor adaptor = new RecyclerAdaptor(accounts);
        adaptor.setListener(
                new RecyclerAdaptor.ItemClickListener() {
                    @Override
                    public void onClick(View v, Account account) {
                        v.setTransitionName("transform_to_account_view");
                        Intent intent = new Intent(getContext(), ShowAccountActivity.class);
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "transform_to_account_view");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("account", account);
                        intent.putExtras(bundle);
                        if (AnimationConfig.getInstance().isEnableAnimation()){
                            startActivity(intent, options.toBundle());
                        }else {
                            startActivity(intent);
                        }
                    }
                }
        );

        RecyclerView recyclerView = findViewBy(R.id.account_card_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptor);
    }

    @Override
    public void updateTags(@NonNull List<Tag> tags) {
        ChipGroup chipGroup = findViewBy(R.id.chips_container);
        if (chipGroup.getChildCount() != 0)
            chipGroup.removeAllViews();
        @SuppressLint("ResourceType") Chip defaultChip =
                (Chip) LayoutInflater.from(getContext()).inflate(R.xml.chip_item, chipGroup, false);
        defaultChip.setText("全部");
        defaultChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int color;
            if (isChecked) {
                color = ResourcesCompat.getColor(getResources(), R.color.chip_background_color, null);
            } else {
                color = ResourcesCompat.getColor(getResources(), R.color.chip_background_color, null);
            }
            defaultChip.setChipBackgroundColor(ColorStateList.valueOf(color));
        });
        chipGroup.addView(defaultChip);

        for (Tag tag :
                tags) {
            @SuppressLint("ResourceType") Chip newChip =
                    (Chip) LayoutInflater.from(getContext()).inflate(R.xml.chip_item, chipGroup, false);
            newChip.setText(tag.getName());
            newChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int color;
                if (isChecked) {
                    if (null == tag.getColor() || tag.getColor().equals(""))
                        color = ColorTransform.from("#FFBB86FC");
                    else
                        color = ColorTransform.from(tag.getColor());
                    presenter.update(tag);
                } else {
                    color = ResourcesCompat.getColor(getResources(), R.color.chip_background_color, null);
                }
                newChip.setChipBackgroundColor(ColorStateList.valueOf(color));
            });
            chipGroup.addView(newChip);
        }
        Chip childAt = (Chip) chipGroup.getChildAt(0);
        childAt.setChecked(true);
    }
}
