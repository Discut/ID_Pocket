package com.discut.pocket.dao;

import com.discut.pocket.bean.account.AccountUsed;

import java.util.List;

public interface IAccountUsedDao {
    boolean insert(AccountUsed accountUsed);

    List<AccountUsed> find(int number);

    List<AccountUsed> queryAll();
}
