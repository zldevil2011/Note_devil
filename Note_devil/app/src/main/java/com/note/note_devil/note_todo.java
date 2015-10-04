package com.note.note_devil;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TabHost;
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
    private ListView note_list;
    public int MID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        getTodoList();
    }
    public void getTodoList(){
        note_list = (ListView)findViewById(R.id.note_list);
        List<Map<String, Object>> list_items = new ArrayList<Map<String, Object>>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
//        Cursor c = db.query("note_list",{"note_status"},"0",null,null,null,null);//查询并获得游标
        Cursor c = db.rawQuery("select * from note_list where note_status=?",new String[]{"0"});
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String title = c.getString(c.getColumnIndex("note_title"));
            String id = c.getString(c.getColumnIndex("_id"));
            String content = c.getString(c.getColumnIndex("note_content"));
            String note_status_choice = c.getString(c.getColumnIndex("note_status")).endsWith("0") ? "Todo": "Finished";
            listitem.put("title", title);
            listitem.put("id", id);
            listitem.put("content", content);
            if(note_status_choice.equals("Todo")){
                listitem.put("note_status", note_status_choice);
            }else{
                listitem.put("note_status_finished", note_status_choice);
            }

            list_items.add(listitem);
            c.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list_items, R.layout.activity_note_item,
                new String[]{"title", "id", "content", "note_status", "note_status_finished"},
                new int[]{R.id.title,R.id.id, R.id.content,R.id.note_status, R.id.note_status_finished});
        note_list.setAdapter(adapter);
        note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView note_list = (ListView) parent;
                HashMap<String, String> map = (HashMap<String, String>) note_list.getItemAtPosition(position);
                String title = map.get("title");
                String n_id = map.get("id");
                String content = map.get("content");
                Log.v("zl_debug", title);
                Log.v("zl_debug", n_id);
                Intent intent_note_edit = new Intent(note_todo.this, note_edit.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", content);
                bundle.putString("n_id", n_id);
                SharedPreferences settings = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("title", title);
                editor.putString("content", content);
                editor.putString("n_id", n_id);
                editor.commit();
                TabHost tab = ((TabActivity) getParent()).getTabHost();
                tab.setCurrentTab(3);
//                intent_note_edit.putExtras(bundle);
//                startActivity(intent_note_edit);
            }
        });
        ItemOnLongClick1();
    }
    private void ItemOnLongClick1() {
        note_list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "Todo");
                menu.add(0, 1, 0, "Finished");
                menu.add(0, 2, 0, "delete");
            }
        });
    }

    // 长按菜单响应函数
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        MID = (int) info.position;// 这里的info.id对应的就是数据库中_id的值
        Log.v("zl_debug_info_id", String.valueOf(MID));

        HashMap<String, String> map = (HashMap<String, String>) note_list.getItemAtPosition(MID);
        String title = map.get("title");
        Log.v("zl_debug_title", title);
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        ContentValues cv = new ContentValues();
        String whereClause = "note_title=?";//修改条件
        String[] whereArgs = {title};

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "Todo",Toast.LENGTH_SHORT).show();
                cv.put("note_status", "0");
                db.update("note_list", cv, whereClause, whereArgs);//执行修改
                break;
            case 1:
                Toast.makeText(this, "Finished",Toast.LENGTH_SHORT).show();
                cv.put("note_status", "1");
                db.update("note_list", cv, whereClause, whereArgs);//执行修改
                break;
            case 2:
                Toast.makeText(this, "Delete",Toast.LENGTH_SHORT).show();
                db.delete("note_list", whereClause, whereArgs);//执行删除
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
