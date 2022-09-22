package com.discut.pocket.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.discut.pocket.bean.Account;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.model.intf.ISaveAccountModel;

import java.util.Iterator;
import java.util.List;

public class SaveAccountModel implements ISaveAccountModel {
    private SQLiteDatabase db;

    protected void setDb(@NonNull SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public boolean saveAll(@NonNull List<Account> accounts) {
        for (Account next : accounts) {
            save(next);
        }
        return true;
    }

    @Override
    public boolean save(@NonNull Account account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account.getAccount());
        contentValues.put("title", account.getTitle());
        contentValues.put("password", account.getPassword());
        contentValues.put("note", account.getNote());
        if (account.getStatus() == AccountStatus.NEW) {
            db.insert("account", null, contentValues);
            return true;
        } else if (account.getStatus() == AccountStatus.MODIFIED) {
            db.update("account", contentValues, "id=?", new String[]{account.getId()});
            return true;
        } else {
            return false;
        }
    }
}
