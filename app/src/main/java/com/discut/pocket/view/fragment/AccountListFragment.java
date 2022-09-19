package com.discut.pocket.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.RecyclerAdaptor;
import com.discut.pocket.bean.ListItem;
import com.discut.pocket.mvp.BaseFragment;
import com.discut.pocket.view.MainActivity;
import com.discut.pocket.view.ShowAccountActivity;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.List;

public class AccountListFragment extends BaseFragment {
    @Override
    protected void initView() {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));

        NestedScrollView listView = findViewBy(R.id.account_list);
        listView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    if (scrollY > oldScrollY) {
                        activity.hideBottomBar();
                    } else {
                        activity.showBottomBar();
                    }
                }
            }
        });


        List<ListItem> listItems = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ListItem item = new ListItem();
            item.title = "标题" + i;
            item.des = "这是Card的介绍这是Card的介绍" + i;
            item.account = "Siiiro@outlook.com";
            item.chips = new String[]{"Chip1", "chip2"};
            listItems.add(item);
        }
        RecyclerAdaptor adaptor = new RecyclerAdaptor(listItems);
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


        RecyclerView view = findViewBy(R.id.account_card_content);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setAdapter(adaptor);

    }

    @Override
    protected int baseLayoutId() {
        return R.layout.account_list_fragment_layout;
    }
}
