package com.discut.pocket.presenter;

import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.presenter.intf.ISettingPresenter;
import com.discut.pocket.view.intf.ISettingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SettingPresenter extends BasePresenter<ISettingView> implements ISettingPresenter {
    @Override
    public List<Account> importAccounts(InputStream fileStream) {
        List<Account> accountList = new ArrayList<>();
        try (fileStream) {
            int available = fileStream.available();
            byte[] bytes = new byte[available];
            if (fileStream.read(bytes) <= 0) {
                return accountList;
            }
            String s = new String(bytes);
            JSONObject jsonObject = new JSONObject(s);

            JSONArray accounts = jsonObject.getJSONArray("accounts");
            int length = accounts.length();
            for (int i = 0; i < length; i++) {
                Account account = new Account();
                JSONObject accountJson = accounts.getJSONObject(i);
                account.setTitle((String) accountJson.get("title"));
                account.setAccount((String) accountJson.get("account"));
                account.setPassword((String) accountJson.get("password"));
                account.setNote((String) accountJson.get("note"));
                account.setStatus(AccountStatus.NEW);
                JSONArray tagsJson = accountJson.getJSONArray("tags");
                int tagsLength = tagsJson.length();
                Tag[] tags = new Tag[tagsLength];
                for (int j = 0; j < tagsLength; j++) {
                    Tag tag = new Tag();
                    JSONObject tagJson = tagsJson.getJSONObject(j);
                    tag.setName((String) tagJson.get("name"));
                    String color = "";
                    if (!tagJson.isNull("color"))
                        color = (String) tagJson.get("color");
                    tag.setColor(color);
                    tags[j] = tag;
                }
                account.setTags(tags);
                accountList.add(account);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public boolean saveAccounts(List<Account> accounts) {
        AccountModelAbstractFactory factory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = factory.create();
        baseAccountModel.save(accounts);
        return true;
    }
}
