package com.discut.pocket.presenter;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IAccountListView;

import java.util.ArrayList;
import java.util.List;

public class AccountListPresenter extends BasePresenter<IAccountListView> {
    public void update(Tag selectedTag){
        IAccountListView iAccountListView = reference.get();

        ArrayList<Account> accountList = new ArrayList<>();
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
        }
        iAccountListView.updateAccountList(accountList);

        List<Tag> tags = new ArrayList<>();

        for (int i =0;i<4;i++){
            Tag tag = new Tag();
            tag.setName("Tag"+i);
            tag.setColor("#d7c5e6");
            tags.add(tag);
        }

        iAccountListView.updateTags(tags);
    }
}
