package com.discut.pocket.view.intf;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.mvp.IView;

import java.util.List;

public interface IHomeView extends IView {
    void updateAccountList(List<Account> accounts);
}
