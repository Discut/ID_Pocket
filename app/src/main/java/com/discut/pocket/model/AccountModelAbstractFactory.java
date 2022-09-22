package com.discut.pocket.model;

import com.discut.pocket.model.intf.IReadAccountModel;
import com.discut.pocket.model.intf.ISaveAccountModel;

public abstract class AccountModelAbstractFactory {
    private BaseAccountModel accountModel;

    protected abstract IReadAccountModel getReadModel();
    protected abstract ISaveAccountModel getSaveModel();
    protected abstract BaseAccountModel getAccountModel();
    protected abstract void init();

    public BaseAccountModel create(){
        init();
        accountModel = getAccountModel();
        accountModel.saveModel = getSaveModel();
        accountModel.readModel = getReadModel();
        return accountModel;
    }
}
