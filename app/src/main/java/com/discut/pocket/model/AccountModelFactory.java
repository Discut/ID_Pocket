package com.discut.pocket.model;

public class AccountModelFactory extends AccountModelAbstractFactory{
    @Override
    protected IReadAccountModel getReadModel() {
        return null;
    }

    @Override
    protected ISaveAccountModel getSaveModel() {
        return null;
    }

    @Override
    protected BaseAccountModel getAccountModel() {
        return null;
    }
}
