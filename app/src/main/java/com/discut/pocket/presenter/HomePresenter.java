package com.discut.pocket.presenter;

import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IHomeView;

import java.util.List;

public class HomePresenter extends BasePresenter<IHomeView> {
    private List<Account> accountList;

    public void update() {

        AccountModelFactory accountModelFactory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = accountModelFactory.create();
        accountList = baseAccountModel.getAll();

/*        accountList.removeIf(next -> next.getStatus() == AccountStatus.DELETED);*/
        IHomeView iHomeView = reference.get();
        iHomeView.updateAccountList(accountList);

    }
}
