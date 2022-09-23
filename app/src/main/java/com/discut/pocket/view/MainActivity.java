package com.discut.pocket.view;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.discut.pocket.R;
import com.discut.pocket.configuration.AnimationConfig;
import com.discut.pocket.configuration.SystemConfig;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.view.intf.IMainView;
import com.discut.pocket.presenter.MainPagePresenter;
import com.discut.pocket.view.fragment.AccountListFragment;
import com.discut.pocket.view.fragment.HomeFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;

public class MainActivity extends BaseActivity<MainPagePresenter, IMainView> implements IMainView {
    private Fragment[] fragments;
    private FragmentMode currentFragment = FragmentMode.NO_FRAGMENT;
    private MenuItem lastItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
// 容器转换设置
        // by the container transform transition
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        // Keep system bars (status bar, navigation bar) persistent throughout the transition.
        getWindow().setSharedElementsUseOverlay(false);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int containerLayout() {
        return R.layout.main_layout;
    }

    @Override
    protected MainPagePresenter createPresenter() {
        return new MainPagePresenter();
    }

    @Override
    protected void init() {
        super.init();
        fragments = new Fragment[]{new HomeFragment(), new AccountListFragment()};
        initListener();

        // 读取配置的启动页
        if (SystemConfig.getInstance().getBootPage().equals("最近使用")){
            switchFragmentTo(FragmentMode.HOME);
        }else {
            switchFragmentTo(FragmentMode.LIST);
        }
    }

    private void initListener() {
        @SuppressLint("CutPasteId") BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                switchFragmentTo(FragmentMode.HOME);
                lastItem = (item);
            } else if (item.getItemId() == R.id.menu_list) {
                switchFragmentTo(FragmentMode.LIST);
                lastItem = (item);
            } else if (item.getItemId() == R.id.menu_settings) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
            }
            return false;
        });

        FloatingActionButton fba = findViewById(R.id.add_account);
        fba.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditAccountActivity.class);
            //ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "shared_element_account_edit");
            if (AnimationConfig.getInstance().isEnableAnimation()){
                @SuppressLint("CutPasteId") ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.bottomAppBar), "edit_account_transform");
                startActivity(intent, options.toBundle());
            }else {
                startActivity(intent);
            }
        });
    }

    @Override
    public void showMsg(String msg) {

    }

    private void switchFragmentTo(FragmentMode fragment) {
        Log.d(TAG, "switchFragmentTo: 切换到" + fragment);
        if (currentFragment == fragment)
            return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!fragments[fragment.ordinal()].isAdded()) {
            fragmentTransaction.add(R.id.fragment_content, fragments[fragment.ordinal()]).commit();
            //fragmentTransaction.replace(R.id.fragment_content, fragments[fragment.ordinal()]).commit();
        } else {
            fragmentTransaction.show(fragments[fragment.ordinal()]).commit();
        }
        if (currentFragment != FragmentMode.NO_FRAGMENT)
            fragmentTransaction.hide(fragments[currentFragment.ordinal()]);
        currentFragment = fragment;
    }

    private enum FragmentMode {
        HOME, LIST, NO_FRAGMENT
    }

    public void hideBottomBar(){
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.performHide();
    }

    public void showBottomBar(){
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomAppBar.performShow();
    }
}