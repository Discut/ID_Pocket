package com.discut.pocket.view.intf;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.mvp.IView;

import java.util.List;

public interface IAccountListView extends IView {
    void updateAccountList(List<Account> accounts);
    void updateTags(List<Tag> tags);
}
