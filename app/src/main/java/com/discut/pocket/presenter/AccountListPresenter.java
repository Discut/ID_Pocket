package com.discut.pocket.presenter;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IAccountListView;

import java.util.ArrayList;
import java.util.List;

public class AccountListPresenter extends BasePresenter<IAccountListView> {
    boolean key = false;
    public void update(Tag selectedTag){
    // TODO 在没有进行account更新时，请不要更新界面 这样会吧item一起更新 导致item丢失transitionName 动画无法正确返回
        if (key)
            return;
        IAccountListView iAccountListView = reference.get();

        ArrayList<Account> accountList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Account account = new Account();
            account.setTitle("Steam账号"+i);
            account.setAccount("Siiiro@outlook.com"+i);
            account.setNote("这是一个账号备注，账号注册于2002年。"+i);
            account.setPassword("23199288530.。"+i);

            Tag tag = new Tag();
            tag.setName("Google");
            Tag tag2 = new Tag();
            tag2.setName("Steam");

            account.setTags(new Tag[]{tag, tag2});
            accountList.add(account);
        }
        iAccountListView.updateAccountList(accountList);

        List<Tag> tags = new ArrayList<>();

        for (int i =0;i<4;i++){
            Tag tag = new Tag();
            tag.setName("Tag"+i);
            tag.setColor("#d7c5e6");
            tags.add(tag);
        }
key=true;
        iAccountListView.updateTags(tags);
    }
}
