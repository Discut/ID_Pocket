package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.account.AccountUsed;
import com.discut.pocket.dao.IAccountUsedDao;
import com.discut.pocket.dao.iplm.AccountUsedDao;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.presenter.intf.IShowAccountPresenter;
import com.discut.pocket.view.intf.IShowAccountView;

import java.util.Date;

public class ShowAccountPresenter extends BasePresenter<IShowAccountView> implements IShowAccountPresenter {

    private final IAccountUsedDao accountUsedDao;

    public ShowAccountPresenter() {
        accountUsedDao = new AccountUsedDao();
    }

    @Override
    public void deleteAccount(Account account) {
        AccountModelAbstractFactory accountModelFactory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = accountModelFactory.create();
        baseAccountModel.delete(account);
    }

    @Override
    public void recordAccount(Account account) {
        Thread thread = new Thread(() -> {
            if (account == null) {
                return;
            }
            AccountUsed accountUsed = new AccountUsed();
            accountUsed.setAccountId(Integer.parseInt(account.getId()));
            accountUsed.setDate(new Date().toString());
            this.accountUsedDao.insert(accountUsed);
        });
        thread.start();
    }
}
