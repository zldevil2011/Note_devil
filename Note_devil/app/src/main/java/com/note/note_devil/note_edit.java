package com.note.note_devil;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.note.note_devil.com.database.note.note_devil.DatabaseHelper;

public class note_edit extends Activity {
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        bundle = this.getIntent().getExtras();
        if(bundle != null){
            Log.v("zl_debug", "fine");
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            String n_id = bundle.getString("n_id");
            EditText title_e = (EditText)findViewById(R.id.title), content_e = (EditText)findViewById(R.id.content);
            title_e.setText(title);
            content_e.setText(content);
        }else{
            Log.v("zl_debug", "get extra failed");
        }
        SharedPreferences settings = getSharedPreferences("setting", 0);
        Log.v("zl_debug", String.valueOf(settings.getAll()));
        if(String.valueOf(settings.getAll()) != "{}"){
            String title = settings.getString("title", "");
            String content = settings.getString("content", "");
            String id = settings.getString("n_id", "");
            EditText title_e = (EditText)findViewById(R.id.title), content_e = (EditText)findViewById(R.id.content);
            title_e.setText(title);
            content_e.setText(content);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("title");
            editor.remove("content");
            editor.remove("n_id");
            editor.commit();
            listening(1, id);
        }else{
            listening(0, "-1");
        }

    }
    public void listening(final int edit_type, final String note_id){
        Button save = (Button)findViewById(R.id.save), cancel = (Button)findViewById(R.id.cancel);
        final EditText title = (EditText)findViewById(R.id.title), content = (EditText)findViewById(R.id.content);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("zl_debug", "click_save");
                String note_title = title.getText().toString(), note_content = content.getText().toString();
                Log.v("zl_debug_note_title", note_title);
                Log.v("zl_debug_note_content", note_content);
                Boolean res = false;
                if(edit_type == 0){
                    res = save_note(note_title, note_content, 0, "0");
                }else{
                    res = save_note(note_title, note_content, 1, note_id);
                }
                if(res == true){
                    Toast.makeText(getApplicationContext(), "新建成功",Toast.LENGTH_SHORT).show();
                    TabHost tab = ((TabActivity)getParent()).getTabHost();
                    tab.setCurrentTab(0);
                }else{
                    Toast.makeText(getApplicationContext(), "新建失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Boolean save_note(String title, String content, int type, String id){
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        ContentValues cv = new ContentValues();
        if(type == 0){
            Log.v("zl_debug", "create new item");
            cv.put("note_title", title);
            cv.put("note_content", content);
            cv.put("note_status", "0");
            db.insert("note_list", null, cv);
            return true;
        }else{
            Log.v("zl_debug", "update table");
            cv.put("note_title", title);
            cv.put("note_content", content);
            cv.put("note_status", "0");
            String whereClause = "_id=?";//修改条件
            String[] whereArgs = {id};//修改条件的参数
            db.update("note_list", cv, whereClause, whereArgs);//执行修改
            return true;
        }
//        return false;
    }
}
