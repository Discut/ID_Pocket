package com.discut.pocket.presenter;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.dao.ITagDao;
import com.discut.pocket.dao.iplm.TagDao;
import com.discut.pocket.model.AccountModelAbstractFactory;
import com.discut.pocket.model.AccountModelFactory;
import com.discut.pocket.model.BaseAccountModel;
import com.discut.pocket.mvp.BasePresenter;
import com.discut.pocket.view.intf.IAccountListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AccountListPresenter extends BasePresenter<IAccountListView> {
    boolean key = false;

    private ITagDao tagDao = new TagDao();


    public void update(Tag selectedTag) {
        // TODO 在没有进行account更新时，请不要更新界面 这样会吧item一起更新 导致item丢失transitionName 动画无法正确返回
//        if (key)
//            return;
        IAccountListView iAccountListView = reference.get();


        AccountModelAbstractFactory accountModelAbstractFactory = new AccountModelFactory();

        BaseAccountModel baseAccountModel = accountModelAbstractFactory.create();
        List<Account> all = baseAccountModel.getAll();

        if (selectedTag == null) {
            iAccountListView.updateAccountList(all);
        }else {
            ArrayList<Account> accounts = new ArrayList<>();
            for (Account account: all){
                Iterator<Tag> iterator = Arrays.stream(account.getTags()).iterator();
                while (iterator.hasNext()){
                    if (iterator.next().getName().equals(selectedTag.getName())){
                        accounts.add(account);
                        break;
                    }
                }
            }
            iAccountListView.updateAccountList(accounts);
        }


//        ArrayList<Account> accountList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            Account account = new Account();
//            account.setTitle("Steam账号" + i);
//            account.setAccount("Siiiro@outlook.com" + i);
//            account.setNote("这是一个账号备注，账号注册于2002年。" + i);
//            account.setPassword("23199288530.。" + i);
//
//            Tag tag = new Tag();
//            tag.setName("Google");
//            Tag tag2 = new Tag();
//            tag2.setName("Steam");
//
//            account.setTags(new Tag[]{tag, tag2});
//            accountList.add(account);
//        }
//        iAccountListView.updateAccountList(accountList);

        List<Tag> tags = tagDao.getAll();


//        for (int i = 0; i < 4; i++) {
//            Tag tag = new Tag();
//            tag.setName("Tag" + i);
//            tag.setColor("#d7c5e6");
//            tags.add(tag);
//        }
        key = true;
        iAccountListView.updateTags(tags);
    }
}
