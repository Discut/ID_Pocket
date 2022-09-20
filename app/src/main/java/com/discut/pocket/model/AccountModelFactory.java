package com.discut.pocket.model;

import com.discut.pocket.model.intf.IReadAccountModel;
import com.discut.pocket.model.intf.ISaveAccountModel;

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
