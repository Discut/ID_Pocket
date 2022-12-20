package com.discut.pocket.model;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.model.intf.IReadAccountModel;

import java.util.ArrayList;
import java.util.List;

public class ReadAccountModel implements IReadAccountModel {
    private SQLiteDatabase db;

    protected void setDb(@NonNull SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public List<Account> readAll() {
        List<Account> accounts = new ArrayList<>();
        try (@SuppressLint("Recycle") Cursor cursor = db.query("account", new String[]{"id", "title", "account", "password", "note"}, null, null, null, null, null)
        ) {
            if (cursor.moveToFirst()) {
                if (cursor.getCount() != 0) {
                    do {
                        Account account = new Account();
                        account.setId(String.valueOf(cursor.getInt(0)));
                        account.setTitle(cursor.getString(1));
                        account.setAccount(cursor.getString(2));
                        account.setPassword(cursor.getString(3));
                        account.setNote(cursor.getString(4));
                        List<Tag> tags = new ArrayList<Tag>();

                        try (@SuppressLint("Recycle") Cursor cursor1 = db.query("tag", new String[]{"name", "account_id", "color"}, "account_id=" + account.getId(), null, null, null, null)
                        ) {
                            if (cursor1.moveToFirst()) {
                                if (cursor1.getCount() != 0) {
                                    do {
                                        Tag tag = new Tag();
                                        tag.setName(cursor1.getString(0));
                                        tag.setAccountId(cursor1.getInt(1));
                                        tag.setColor(cursor1.getString(2));
                                        tags.add(tag);
                                    } while (cursor1.moveToNext());
                                }

                            }
                        }
                        Tag[] arrayTag = new Tag[tags.size()];
                        for (int i = 0; i < tags.size(); i++) {
                            arrayTag[i] = tags.get(i);
                        }
                        account.setTags(arrayTag);
                        accounts.add(account);
                    } while (cursor.moveToNext());
                }

            }
        }


        return accounts;
    }
}
