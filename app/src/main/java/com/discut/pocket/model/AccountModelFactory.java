package com.discut.pocket.model;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import com.discut.pocket.model.intf.IReadAccountModel;
import com.discut.pocket.model.intf.ISaveAccountModel;
import com.discut.pocket.utils.DatabaseUtil;

public class AccountModelFactory extends AccountModelAbstractFactory {

    private SQLiteDatabase db;

    @Override
    protected IReadAccountModel getReadModel() {
        ReadAccountModel readAccountModel = new ReadAccountModel();
        readAccountModel.setDb(db);
        return readAccountModel;
    }

    @Override
    protected ISaveAccountModel getSaveModel() {
        SaveAccountModel saveAccountModel = new SaveAccountModel();
        saveAccountModel.setDb(db);
        return saveAccountModel;
    }

    @Override
    protected BaseAccountModel getAccountModel() {
        return new AccountModel();
    }

    @SuppressLint("SdCardPath")
    @Override
    protected void init() {
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/" + "com.discut.pocket/accounts.db", null);
        String accounts_table = "create table account(id integer primary key autoincrement,title text,account text,password text,note text)";
        if (!DatabaseUtil.isTableExist(db, "account"))
            db.execSQL(accounts_table);

        String tag_table = "CREATE TABLE tag(name text,account_id integer,color NVARCHAR(32),FOREIGN KEY (account_id) REFERENCES account(id),PRIMARY KEY (name,account_id))";
        if (!DatabaseUtil.isTableExist(db, "tag"))
            db.execSQL(tag_table);

        String click_account_table = "CREATE TABLE click_account(account_id integer,FOREIGN KEY (account_id) REFERENCES account(id))";
        if (!DatabaseUtil.isTableExist(db, "click_account"))
            db.execSQL(click_account_table);
    }
}
