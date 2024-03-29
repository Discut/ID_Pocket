package com.discut.pocket.model;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.AccountStatus;

import java.util.List;

public class AccountModel extends BaseAccountModel {
    List<Account> accounts;

    protected AccountModel() {
    }

    @Override
    public boolean delete(Account account) {
        return saveModel.delete(account);
    }

    @Override
    public void update() {
        if (accounts != null) {
            for (Account account : accounts) {
                boolean b = account.getStatus() == AccountStatus.NEW;
                if (b) {
                    saveModel.save(account);
                }
            }
            accounts = readModel.readAll();
        }
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

    @Override
    public void update(Account account) {
        if (account.getStatus() == AccountStatus.MODIFIED) {
            saveModel.update(account);
        }
    }


}
