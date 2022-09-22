package com.discut.pocket.model;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.AccountStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountModel extends BaseAccountModel{
    List<Account> accounts;
    protected AccountModel() {
    }

    @Override
    public void update() {

    }

    @Override
    public List<Account> getAll() {
        if (accounts == null) {
            accounts = readModel.readAll();
        }
        return accounts;
    }

    @Override
    public void saveAll() {
        saveModel.saveAll(accounts);
    }

    @Override
    public void save(List<Account> accounts) {
        saveModel.saveAll(accounts);
    }
}
