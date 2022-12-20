package com.discut.pocket.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.AccountStatus;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.dao.ITagDao;
import com.discut.pocket.dao.iplm.TagDao;
import com.discut.pocket.model.intf.ISaveAccountModel;

import java.util.Arrays;
import java.util.List;

public class SaveAccountModel implements ISaveAccountModel {
    private final ITagDao tagDao = new TagDao();
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
            long insert = db.insert("account", null, contentValues);
            if (insert > 0) {
                try (Cursor query = db.query("account", null, null, null, null, null, "id DESC", "0,1")) {
                    if (query.moveToFirst()) {
                        if (query.getCount() != 0) {
                            do {
                                for (Tag tag : account.getTags()) {
                                    tag.setAccountId(Integer.parseInt(query.getString(0)));
                                }
                            } while (query.moveToNext());
                        }
                    }
                }
            }
            saveTags(Arrays.asList(account.getTags()));
            return true;
        } else if (account.getStatus() == AccountStatus.MODIFIED) {
            db.update("account", contentValues, "id=?", new String[]{account.getId()});
            saveTags(Arrays.asList(account.getTags()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Account account) {
        ITagDao tagDao = new TagDao();
        int delete = db.delete("account", "id=?", new String[]{account.getId()});
        for (Tag tag : account.getTags()) {
            tagDao.delete(tag);
        }
        return delete > 0;
    }

    @Override
    public boolean update(Account account) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("account", account.getAccount());
        contentValues.put("title", account.getTitle());
        contentValues.put("password", account.getPassword());
        contentValues.put("note", account.getNote());

        ITagDao tagDao = new TagDao();
        tagDao.deleteTagsBy(account.getId());
        for (Tag tag : account.getTags()) {
            tagDao.insert(tag);
        }
        return db.update("account", contentValues, "id=?", new String[]{account.getId()}) > 0;
    }

    private void saveTag(@NonNull Tag tag) {
        if (!isExist(tag)) {
            tagDao.insert(tag);
        }
    }

    private void saveTags(List<Tag> tags) {
        for (Tag tag :
                tags) {
            saveTag(tag);
        }
    }

    private boolean isExist(Tag tag) {
        for (Tag tag1 : tagDao.getAll()) {
            if (tag1.getName().equals(tag.getName())) {
                return true;
            }
        }
        return false;
    }
}
