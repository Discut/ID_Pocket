package com.discut.pocket.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.RecyclerAdaptor;
import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.BaseFragment;
import com.discut.pocket.presenter.HomePresenter;
import com.discut.pocket.view.intf.IHomeView;
import com.discut.pocket.view.ShowAccountActivity;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomePresenter, IHomeView> implements IHomeView {

    @Override
    protected void initView(View view) {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.update();
    }

    @Override
    protected void initData() {
/*        accountList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Account account = new Account();
            account.setTitle("账号标题"+i);
            account.setAccount("账号内容"+i);
            account.setNote("账号备注"+i);
            account.setPassword("账号密码"+i);

            Tag tag = new Tag();
            tag.setName("12");
            Tag tag2 = new Tag();
            tag2.setName("34");

            account.setTags(new Tag[]{tag, tag2});
            accountList.add(account);
        }*/
    }

    @Override
    protected int baseLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void updateAccountList(List<Account> accounts) {
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

}
