package com.discut.pocket.model.intf;

import com.discut.pocket.bean.account.Account;

import java.util.List;

public interface ISaveAccountModel {
    boolean saveAll(List<Account> accounts);

    boolean save(Account account);

    boolean delete(Account account);
}
