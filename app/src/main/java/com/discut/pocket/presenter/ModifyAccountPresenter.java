package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.presenter.intf.IModifyAccountPresenter;
import com.discut.pocket.view.intf.IModifyAccountView;

public class ModifyAccountPresenter extends BasePresenter<IModifyAccountView> implements IModifyAccountPresenter {
    @Override
    public void update(Account account) {
        AccountModelAbstractFactory factory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = factory.create();
        baseAccountModel.update(account);
    }
}
