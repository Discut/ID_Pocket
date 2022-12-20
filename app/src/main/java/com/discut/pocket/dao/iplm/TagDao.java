package com.discut.pocket.dao.iplm;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import com.discut.pocket.bean.Tag;
import com.discut.pocket.dao.BaseDao;
import com.discut.pocket.dao.ITagDao;

import java.util.ArrayList;
import java.util.List;

public class TagDao extends BaseDao<Tag> implements ITagDao {
    @Override
    protected String tableSql() {
        return "CREATE TABLE tag(name text,account_id integer,color NVARCHAR(32),FOREIGN KEY (account_id) REFERENCES account(id),PRIMARY KEY (name,account_id))";
    }

    @Override
    protected String tableName() {
        return "tag";
    }

    @Override
    public boolean insert(Tag tag) {
        ContentValues values = getContentValuesOf(tag);
        long insert = getDB().insert(tableName(), null, values);
        return insert > 0;
    }

    @Override
    public List<Tag> getAll() {
        @SuppressLint("Recycle")
        Cursor cursor = getDB().query(tableName(), new String[]{"name", "account_id", "color"}, null, null, null, null, null);
        List<Tag> tags = new ArrayList<>();
        if (cursor.moveToFirst()) {
            if (cursor.getCount() != 0) {
                do {
                    Tag tag = new Tag();
                    tag.setName(cursor.getString(0));
                    tag.setAccountId(cursor.getInt(1));
                    tag.setColor(cursor.getString(2));
                    tags.add(tag);
                } while (cursor.moveToNext());
            }
        }
        return tags;
    }

    @Override
    public boolean delete(Tag tag) {
        int delete = getDB().delete(tableName(), "name=? AND account_id=?", new String[]{tag.getName(), String.valueOf(tag.getAccountId())});
        return delete > 0;
    }

    @Override
    public boolean update(Tag tag) {
        ContentValues contentValues = getContentValuesOf(tag);
        int update = getDB().update(tableName(), contentValues, "name=?", new String[]{tag.getName()});
        return update > 0;
    }

    @Override
    protected ContentValues getContentValuesOf(Tag tag) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", tag.getName());
        contentValues.put("account_id", tag.getAccountId());
        contentValues.put("color", tag.getColor());
        return contentValues;
    }
}
