package com.discut.pocket.model;

import androidx.annotation.VisibleForTesting;

import com.discut.pocket.bean.account.Account;

import junit.framework.TestCase;

import java.util.List;

public class AccountModelAbstractFactoryTest extends TestCase {

    @VisibleForTesting
    public void testCreate() {
        AccountModelFactory accountModelFactory = new AccountModelFactory();
        BaseAccountModel baseAccountModel = accountModelFactory.create();
        List<Account> all = baseAccountModel.getAll();

    }
}