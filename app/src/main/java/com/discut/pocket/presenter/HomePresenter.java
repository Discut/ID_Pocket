package com.discut.pocket.presenter;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IHomeView;

import java.util.ArrayList;

public class HomePresenter extends BasePresenter<IHomeView> {

    public void update(){
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

        IHomeView iHomeView = reference.get();
        iHomeView.updateAccountList(accountList);
    }
}
