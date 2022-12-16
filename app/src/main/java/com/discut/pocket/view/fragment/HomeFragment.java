package com.discut.pocket.view.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.RecyclerAdaptor;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.component.AccountCard;
import com.discut.pocket.component.RecyclerAnimation;
import com.discut.pocket.component.SwipeSelectMode;
import com.discut.pocket.configuration.AnimationConfig;
import com.discut.pocket.mvp.BaseFragment;
import com.discut.pocket.presenter.HomePresenter;
import com.discut.pocket.view.ShowAccountActivity;
import com.discut.pocket.view.intf.IHomeView;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.List;

public class HomeFragment extends BaseFragment<HomePresenter, IHomeView> implements IHomeView {


    @Override
    protected void initView(View view) {
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));

        RecyclerView recyclerView = view.findViewById(R.id.account_card_content);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.account_item_anim);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.2f);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        // 自动适应布局
        findViewBy(R.id.top_container).setOnApplyWindowInsetsListener((v, insets) -> {
            int top = WindowInsetsCompat.toWindowInsetsCompat(insets, v).getInsets(WindowInsetsCompat.Type.statusBars()).top;
            v.setPaddingRelative(v.getPaddingStart(), top, v.getPaddingEnd(), v.getPaddingBottom());
            return insets;
        });
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

    private RecyclerAdaptor getAdaptor(List<Account> accounts) {
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
                        if (AnimationConfig.getInstance().isEnableAnimation()) {
                            startActivity(intent, options.toBundle());
                        } else {
                            startActivity(intent);
                        }
                    }
                }
        );
        return adaptor;
    }


    @Override
    public void updateAccountList(List<Account> accounts) {
        RecyclerView recyclerView = findViewBy(R.id.account_card_content);
        if (recyclerView.getAdapter() == null) {
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
                            if (AnimationConfig.getInstance().isEnableAnimation()) {
                                startActivity(intent, options.toBundle());
                            } else {
                                startActivity(intent);
                            }
                        }
                    }
            );
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adaptor);
        }

        RecyclerAnimation recyclerAnimation = new RecyclerAnimation(getContext());


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerAnimation);

        recyclerAnimation.setIcon(
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_add),
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_home))
                .setBackColor(Color.BLUE, Color.GREEN)
                .setMode(SwipeSelectMode.SHARE, SwipeSelectMode.DELETE)
                .setListener(new RecyclerAnimation.SwipeListener() {
                    @Override
                    public void onLeftSwipe(RecyclerView.ViewHolder view, SwipeSelectMode mode) {
                        Log.d("TAG", "onLeftSwipe: 向左滑动" + mode);

                        recyclerAnimation.clearView(recyclerView, view);
/*                        recyclerView.getAdapter().notifyItemChanged(view.getAdapterPosition());
                        recyclerView.setAdapter(getAdaptor(accounts));*/
                        recyclerView.getAdapter().notifyItemChanged(view.getAdapterPosition());


                        recyclerView.setAdapter(getAdaptor(accounts));
                        //itemTouchHelper.attachToRecyclerView(null);
                        //itemTouchHelper.attachToRecyclerView(recyclerView);
                    }

                    @Override
                    public void onRightSwipe(RecyclerView.ViewHolder view, SwipeSelectMode mode) {
                        Log.d("TAG", "onRightSwipe: 向右滑动" + mode);
                        recyclerView.removeView(view.itemView);
                        AccountCard view1 = (AccountCard) view.itemView;
                        Account account = view1.getAccount();
                        account.setStatus(AccountStatus.DELETED);
                        recyclerAnimation.clearView(recyclerView, view);
                        presenter.update();
                        recyclerView.removeView(view.itemView);
                        recyclerView.getAdapter().notifyItemRemoved(view.getAdapterPosition());
                        recyclerView.refreshDrawableState();
                    }
                });


        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
