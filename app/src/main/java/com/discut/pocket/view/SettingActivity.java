package com.discut.pocket.view;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import com.discut.pocket.ModalBottomSheet;
import com.discut.pocket.R;
import com.discut.pocket.adaptor.SelectItemAdaptor;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.component.NormalItem;
import com.discut.pocket.component.SelectItem;
import com.discut.pocket.component.SwitchItem;
import com.discut.pocket.configuration.AnimationConfig;
import com.discut.pocket.configuration.BiometricConfig;
import com.discut.pocket.configuration.SystemConfig;
import com.discut.pocket.configuration.ThemeConfig;
import com.discut.pocket.configuration.ThemeMode;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BaseActivity;
import com.discut.pocket.presenter.SettingPresenter;
import com.discut.pocket.view.intf.ISettingView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SettingActivity extends BaseActivity<SettingPresenter, ISettingView> implements ISettingView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initConfig();

    }

    @Override
    protected int containerLayout() {
        return R.layout.setting_layout;
    }

    private void initConfig() {

        SelectItem selectBoot = findViewById(R.id.select_boot_page);
        switch (SystemConfig.getInstance().getBootPage()) {
            case "最近使用":
                selectBoot.setDetails("已选择「最近使用」");
                break;
            case "账号列表":
                selectBoot.setDetails("已选择「账号列表」");
                break;
        }

        SelectItem selectTheme = findViewById(R.id.select_theme);
        switch (ThemeConfig.getInstance().getMode()) {
            case AUTO:
                selectTheme.setDetails("跟随系统");
                break;
            case LIGHT:
                selectTheme.setDetails("明亮");
                break;
            default:
                selectTheme.setDetails("黑暗");
                break;
        }

        SwitchItem bio = findViewById(R.id.verification);
        if (BiometricConfig.getInstance().isUse()) {
            bio.setDetails("App将在启动时验证您的生物信息");
            bio.setCheck(true);
        } else {
            bio.setDetails("App不会验证生物信息");
            bio.setCheck(false);
        }

        SwitchItem animation = findViewById(R.id.animate_switch);
        if (AnimationConfig.getInstance().isEnableAnimation()) {
            animation.setDetails("某些页面将会加入容器转换");
            animation.setCheck(true);
        } else {
            animation.setDetails("现在已启用默认动画");
            animation.setCheck(false);
        }
    }

    protected void initListener() {
        // 导航键监听
        MaterialToolbar topBar = findViewById(R.id.topAppBar);
        topBar.setNavigationOnClickListener(v -> finish());
        // 主题选择监听
        SelectItem selectTheme = findViewById(R.id.select_theme);
        selectTheme.setAdaptor(new SelectItemAdaptor() {
            @Override
            public int getSelectedOption() {
                return ThemeConfig.getInstance().getMode().ordinal();
            }

            @Override
            public String[] getOptions() {
                return new String[]{"跟随系统", "明亮", "黑暗"};
            }
        });
        selectTheme.setOptionListener((v, option, which) -> {
            Log.d(TAG, "initListener: 选择了" + option + which);
            ThemeConfig.getInstance().setMode(ThemeMode.values()[which]);
            switch (option) {
                case "明亮":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "黑暗":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
            }
            initConfig();
        });
        // 启动页监听
        // 主题选择监听
        SelectItem selectBoot = findViewById(R.id.select_boot_page);
        selectBoot.setAdaptor(new SelectItemAdaptor() {
            @Override
            public int getSelectedOption() {
                String[] strings = {"最近使用", "账号列表"};
                for (int i = 0; i < strings.length; i++) {
                    if (SystemConfig.getInstance().getBootPage().equals(strings[i]))
                        return i;
                }
                return 0;
            }

            @Override
            public String[] getOptions() {
                return new String[]{"最近使用", "账号列表"};
            }
        });
        selectBoot.setOptionListener((v, option, which) -> {
            SystemConfig.getInstance().setBootPage(option);
            initConfig();
        });
        // 生物信息验证监听

        SwitchItem bio = findViewById(R.id.verification);
        bio.setListener(new SwitchItem.CheckedListener() {
            @Override
            public void onCheck(SwitchItem view) {
                BiometricConfig.getInstance().setUse(true);
                initConfig();
            }

            @Override
            public void uncheck(SwitchItem view) {
                BiometricConfig.getInstance().setUse(false);
                initConfig();
            }
        });
        // 动画选择监听器
        SwitchItem animation = findViewById(R.id.animate_switch);
        animation.setListener(new SwitchItem.CheckedListener() {
            @Override
            public void onCheck(SwitchItem view) {
                AnimationConfig.getInstance().setEnableAnimation(true);
                initConfig();
            }

            @Override
            public void uncheck(SwitchItem view) {
                AnimationConfig.getInstance().setEnableAnimation(false);
                initConfig();
            }
        });

        // 导入账号
        NormalItem importAccount = findViewById(R.id.load_account);
        importAccount.setListener(view -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/plain");//无类型限制

            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);

        });

        // 导出账号
        NormalItem exportAccount = findViewById(R.id.export_account);
        exportAccount.setListener(view -> {
            JSONObject jsonObject = new JSONObject();
            AccountModelAbstractFactory factory = new AccountModelFactory();
            BaseAccountModel baseAccountModel = factory.create();
            try {
                JSONArray accountsJson = new JSONArray();
                for (Account account : baseAccountModel.getAll()) {
                    JSONObject accountJson = new JSONObject();
                    accountJson.put("title", account.getTitle());
                    accountJson.put("account", account.getAccount());
                    accountJson.put("password", account.getPassword());
                    accountJson.put("note", account.getNote());
                    JSONArray tagsJson = new JSONArray();
                    for (Tag tag : account.getTags()) {
                        JSONObject tagJson = new JSONObject();
                        tagJson.put("name", tag.getName());
                        tagJson.put("color", tag.getColor());
                        tagsJson.put(tagJson);
                    }
                    accountJson.put("tags", tagsJson);
                    accountsJson.put(accountJson);

                }
                jsonObject.put("accounts", accountsJson);

                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS), "pocket");
                if (!file.exists()) {
                    file.mkdir();
                }
                String path = file.getPath();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                String exportFile = path + File.separator + simpleDateFormat.format(new Date()) + ".txt";
                FileWriter fileWriter = new FileWriter(exportFile);
                fileWriter.write(jsonObject.toString());
                fileWriter.flush();
                fileWriter.close();
                Snackbar make = Snackbar.make(findViewById(R.id.setting_container), "已保存：" + exportFile, Snackbar.LENGTH_SHORT);
                make.show();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    List<Account> accountList = presenter.importAccounts(inputStream);

                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                    materialAlertDialogBuilder.setTitle("确认导入吗？").setMessage("已获取到" + accountList.size() + "条数据").setPositiveButton("确认", (dialog, which) -> {
                        presenter.saveAccounts(accountList);
                    }).setNeutralButton("取消", (dialog, which) -> {

                    }).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
