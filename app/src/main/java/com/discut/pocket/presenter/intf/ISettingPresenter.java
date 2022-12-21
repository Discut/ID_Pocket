package com.discut.pocket.presenter.intf;

import com.discut.pocket.bean.account.Account;

import java.io.InputStream;
import java.util.List;

public interface ISettingPresenter {
    List<Account> importAccounts(InputStream fileStream);

    boolean saveAccounts(List<Account> accounts);
}
