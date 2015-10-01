package com.note.note_devil.com.database.note.note_devil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dell on 2015/10/1.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "note.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table note_list(_id INTEGER PRIMARY KEY AUTOINCREMENT, note_title varchar(20) not null ," +
                " note_content varchar(600) not null, note_status varchar(20) not null );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
