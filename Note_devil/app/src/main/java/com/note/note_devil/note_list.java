package com.note.note_devil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;

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
        final ListView note_list;
        note_list = (ListView)findViewById(R.id.note_list);
        final List<Map<String, Object>> list_items = new ArrayList<Map<String, Object>>();
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        Cursor c = db.query("note_list",null,null,null,null,null,null);//查询并获得游标
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Map<String, Object> listitem = new HashMap<String, Object>();
            String title = c.getString(c.getColumnIndex("note_title"));
            String id = c.getString(c.getColumnIndex("_id"));
            String content = c.getString(c.getColumnIndex("note_content"));
            listitem.put("title", title);
            listitem.put("id", id);
            listitem.put("content", content);
            list_items.add(listitem);
            c.moveToNext();
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list_items, R.layout.activity_note_item,
                new String[]{"title", "id", "content"}, new int[]{R.id.title,R.id.id, R.id.content});
        note_list.setAdapter(adapter);
        note_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView note_list = (ListView)parent;
                HashMap<String, String> map = (HashMap<String, String>) note_list.getItemAtPosition(position);
                String title = map.get("title");
                String n_id = map.get("id");
                String content = map.get("content");
                Log.v("zl_debug", title);
                Log.v("zl_debug", n_id);
                Intent intent_note_edit = new Intent(note_list.this, note_edit.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", content);
                bundle.putString("n_id", n_id);
                bundle.putString("tab_id", "3");
//                MainActivity main = new MainActivity();
//                main.getNoteList();
                intent_note_edit.putExtras(bundle);
                startActivity(intent_note_edit);
            }
        });
    }
}
