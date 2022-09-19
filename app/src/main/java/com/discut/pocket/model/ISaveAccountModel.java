package com.discut.pocket.model;

import com.discut.pocket.bean.Account;

import java.util.List;

public interface ISaveAccountModel {
    boolean saveAll(List<Account> accounts);
    boolean save(Account account);
}
