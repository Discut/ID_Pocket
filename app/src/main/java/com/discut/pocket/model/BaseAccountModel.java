package com.discut.pocket.model;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.model.intf.IReadAccountModel;
import com.discut.pocket.model.intf.ISaveAccountModel;

import java.util.List;

public abstract class BaseAccountModel {
    protected BaseAccountModel(){}
    private List<Account> accounts;

    protected ISaveAccountModel saveModel;
    protected IReadAccountModel readModel;

    public abstract void update();
    public abstract List<Account> getAll();
    public abstract void saveAll();
    public abstract void save(List<Account> accounts);

}
