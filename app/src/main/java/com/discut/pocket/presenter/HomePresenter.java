package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IHomeView;

import java.util.List;

public class HomePresenter extends BasePresenter<IHomeView> {
    private List<Account> accountList;
    boolean key = false;

    public void update(){

        if (accountList == null) {
            AccountModelFactory accountModelFactory = new AccountModelFactory();
            BaseAccountModel baseAccountModel = accountModelFactory.create();
            accountList = baseAccountModel.getAll();
        }

/*        if (!key){
            accountList = new ArrayList<>();
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

            key = true;
        }else {
        }*/
        accountList.removeIf(next -> next.getStatus() == AccountStatus.DELETED);
        IHomeView iHomeView = reference.get();
        iHomeView.updateAccountList(accountList);

    }
}
