package com.discut.pocket.model;

import com.discut.pocket.bean.Account;

import java.util.List;

public abstract class BaseAccountModel {
    private List<Account> accounts;

    protected ISaveAccountModel saveModel;
    protected IReadAccountModel readModel;

    public abstract void update();
    public abstract List<Account> getAll();
    public abstract void saveAll();
    public abstract void save(List<Account> accounts);

}
