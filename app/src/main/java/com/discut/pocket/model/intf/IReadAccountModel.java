package com.discut.pocket.model.intf;

import com.discut.pocket.bean.account.Account;

import java.util.List;

public interface IReadAccountModel {
    List<Account> readAll();
}
