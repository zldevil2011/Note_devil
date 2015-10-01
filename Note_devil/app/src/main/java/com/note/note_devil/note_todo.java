package com.note.note_devil;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.note.note_devil.com.database.note.note_devil.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2015/10/1.
 */
public class note_todo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        getTodoList();
    }
    public void getTodoList(){
        ListView note_list;
        note_list = (ListView)findViewById(R.id.note_list);
        List<Map<String, Object>> list_items = new ArrayList<Map<String, Object>>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
//        Cursor c = db.query("note_list",{"note_status"},"0",null,null,null,null);//查询并获得游标
        Cursor c = db.rawQuery("select * from note_list where note_status=?",new String[]{"0"});
        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i < c.getCount() - 1; i++){
                Map<String, Object> listitem = new HashMap<String, Object>();
                c.move(i);//移动到指定记录
                String title = c.getString(c.getColumnIndex("note_title"));
                listitem.put("title", title);
                list_items.add(listitem);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list_items, R.layout.activity_note_item,
        new String[]{"title"}, new int[]{R.id.title});
        note_list.setAdapter(adapter);

//        note_list.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                Log.v("zl_debug_item", "it's here");
//                Toast.makeText(getApplicationContext(), "新建成功",Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
