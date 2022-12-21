package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.account.AccountUsed;
import com.discut.pocket.dao.IAccountUsedDao;
import com.discut.pocket.dao.iplm.AccountUsedDao;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenter<IHomeView> {
    private List<Account> accountList;

    public void update() {

        AccountModelFactory accountModelFactory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = accountModelFactory.create();
        List<Account> accountList = baseAccountModel.getAll();
        List<Account> list = new ArrayList<>();

        IAccountUsedDao accountUsedDao = new AccountUsedDao();
        for (AccountUsed accountUsed : accountUsedDao.find(10)) {
            for (Account account : accountList) {
                if (account.getId().equals(String.valueOf(accountUsed.getAccountId()))) {
                    list.add(account);
                    accountList.remove(account);
                    break;
                }
            }
        }
        accountList = list;

        /*        accountList.removeIf(next -> next.getStatus() == AccountStatus.DELETED);*/
        IHomeView iHomeView = reference.get();
        iHomeView.updateAccountList(accountList);

    }
}
