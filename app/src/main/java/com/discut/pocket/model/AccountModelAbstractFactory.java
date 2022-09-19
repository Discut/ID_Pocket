package com.discut.pocket.model;

public abstract class AccountModelAbstractFactory {
    private BaseAccountModel accountModel;

    protected abstract IReadAccountModel getReadModel();
    protected abstract ISaveAccountModel getSaveModel();
    protected abstract BaseAccountModel getAccountModel();

    public BaseAccountModel create(){
        accountModel = getAccountModel();
        accountModel.saveModel = getSaveModel();
        accountModel.readModel = getReadModel();
        return accountModel;
    }
}
