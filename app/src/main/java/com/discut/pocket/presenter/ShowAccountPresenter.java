package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IShowAccountView;
import com.discut.pocket.presenter.intf.IShowAccountPresenter;

public class ShowAccountPresenter extends BasePresenter<IShowAccountView> implements IShowAccountPresenter {


    @Override
    public void deleteAccount(Account account) {
        AccountModelAbstractFactory accountModelFactory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = accountModelFactory.create();
        baseAccountModel.delete(account);
    }
}
