package com.discut.pocket.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {
    //判断表是否存在
    public static boolean isTableExist(SQLiteDatabase database, String tableName) {
        String sql = "select count(*) as c from sqlite_master where type ='table' and name =?";
        Cursor c=database.rawQuery(sql, new String[]{tableName});

        if(c.moveToNext()){
            int count = c.getInt(0);
            if(count>0){
                return true;
            }
        }
        c.close();
        return false;
    }
}
