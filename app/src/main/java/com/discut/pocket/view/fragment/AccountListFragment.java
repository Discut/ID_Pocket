package com.discut.pocket.view.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.RecyclerAdaptor;
import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.BaseFragment;
import com.discut.pocket.mvp.IView;
import com.discut.pocket.presenter.AccountListPresenter;
import com.discut.pocket.utils.ColorTransform;
import com.discut.pocket.view.MainActivity;
import com.discut.pocket.view.ShowAccountActivity;
import com.discut.pocket.view.intf.IAccountListView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
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
        presenter.update();
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
        RecyclerAdaptor adaptor = new RecyclerAdaptor(accounts);
        adaptor.setListener(
                new RecyclerAdaptor.ItemClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), ShowAccountActivity.class);
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v, "transform_to_account_view");
                        startActivity(intent, options.toBundle());
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
// todo
        @SuppressLint("ResourceType") TextView defaultChip =
                (TextView) LayoutInflater.from(getContext()).inflate(R.xml.chip_item, chipGroup, false);
        defaultChip.setText("全部");
        chipGroup.addView(defaultChip);
        for (Tag tag :
                tags) {
            @SuppressLint("ResourceType") TextView newChip =
                    (TextView) LayoutInflater.from(getContext()).inflate(R.xml.chip_item, chipGroup, false);
            newChip.setText(tag.getName());
            int color;
            if (null == tag.getColor() || tag.getColor().equals(""))
                color = ColorTransform.from("#FFBB86FC");
            else
                color = ColorTransform.from(tag.getColor());

            newChip.setBackgroundColor(color);
            chipGroup.addView(newChip);
        }
    }
}
