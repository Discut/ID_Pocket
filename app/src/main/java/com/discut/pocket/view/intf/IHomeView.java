package com.discut.pocket.view.intf;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.IView;

import java.util.List;

public interface IHomeView extends IView {
    void updateAccountList(List<Account> accounts);
}
