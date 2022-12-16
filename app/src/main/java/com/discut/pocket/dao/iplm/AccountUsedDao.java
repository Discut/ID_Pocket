package com.discut.pocket.dao.iplm;

import android.content.ContentValues;
import android.database.Cursor;

import com.discut.pocket.bean.account.AccountUsed;
import com.discut.pocket.dao.BaseDao;
import com.discut.pocket.dao.IAccountUsedDao;

import java.util.ArrayList;
import java.util.List;

public class AccountUsedDao extends BaseDao<AccountUsed> implements IAccountUsedDao {
    @Override
    protected String tableSql() {
        return "CREATE TABLE "
                + tableName()
                + "(account_id integer,date DATETIME)";
    }

    @Override
    protected String tableName() {
        return "account_used";
    }

    @Override
    public boolean insert(AccountUsed accountUsed) {
        ContentValues values = getContentValuesOf(accountUsed);
        long insert = getDB().insert(tableName(), null, values);
        return insert > 0;
    }

    @Override
    public List<AccountUsed> find(int number) {
        Cursor cursor = getDB().query(tableName(), null, null, null, null, null, "date ASC", 0 + "," + number);
        List<AccountUsed> accountUsedList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            if (cursor.getCount() != 0) {
                do {
                    AccountUsed accountUsed = new AccountUsed();
                    accountUsed.setAccountId(cursor.getInt(0));
                    accountUsed.setDate(cursor.getString(1));
                    accountUsedList.add(accountUsed);
                } while (cursor.moveToNext());
            }
        }
        return accountUsedList;
    }

    @Override
    public List<AccountUsed> queryAll() {
        Cursor cursor = getDB().query(tableName(), null, null, null, null, null, null, null);
        List<AccountUsed> accountUsedList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            if (cursor.getCount() != 0) {
                do {
                    AccountUsed accountUsed = new AccountUsed();
                    accountUsed.setAccountId(cursor.getInt(0));
                    accountUsed.setDate(cursor.getString(1));
                    accountUsedList.add(accountUsed);
                } while (cursor.moveToNext());
            }
        }
        return accountUsedList;
    }

    @Override
    protected ContentValues getContentValuesOf(AccountUsed accountUsed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_id", accountUsed.getAccountId());
        contentValues.put("date", accountUsed.getDate());
        return contentValues;
    }
}
