package com.note.note_devil;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.note.note_devil.com.database.note.note_devil.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class note_list extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        getAllList();
    }
    public void getAllList(){
        ListView note_list;
        note_list = (ListView)findViewById(R.id.note_list);
        List<Map<String, Object>> list_items = new ArrayList<Map<String, Object>>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        Cursor c = db.query("note_list",null,null,null,null,null,null);//查询并获得游标
//        Cursor c = db.rawQuery("select * from note_list",null);
//        if(c.moveToFirst()){//判断游标是否为空
//            for(int i = 0;i < c.getCount() - 1; i++){
//                Map<String, Object> listitem = new HashMap<String, Object>();
//                c.move(i);//移动到指定记录
//                String title = c.getString(c.getColumnIndex("note_title"));
//                listitem.put("title", title);
//                list_items.add(listitem);
//            }
//        }
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String title = c.getString(c.getColumnIndex("note_title"));
            listitem.put("title", title);
            list_items.add(listitem);
            c.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list_items, R.layout.activity_note_item,
                new String[]{"title"}, new int[]{R.id.title});
        note_list.setAdapter(adapter);
    }
}
