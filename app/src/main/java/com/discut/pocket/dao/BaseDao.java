package com.discut.pocket.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.discut.pocket.utils.DatabaseUtil;

/**
 * Dao基础类
 */
public abstract class BaseDao<T> {
    private static SQLiteDatabase db = null;

    public BaseDao() {
        if (db == null) {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/" + "com.discut.pocket/accounts.db", null);
        }
        if (tableName() != null) {
            if (!tableName().equals("") && !DatabaseUtil.isTableExist(db, tableName())) {
                db.execSQL(tableSql());
            }
        }
    }

    protected abstract String tableSql();

    protected abstract String tableName();

    /**
     * 获取数据库对象
     *
     * @return
     */
    protected SQLiteDatabase getDB() {
        return db;
    }

    protected ContentValues getContentValuesOf(T t) {
        return new ContentValues();
    }

}
