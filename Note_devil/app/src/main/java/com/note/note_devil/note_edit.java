package com.note.note_devil;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        listening();
    }
    public void listening(){
        Button save = (Button)findViewById(R.id.save), cancel = (Button)findViewById(R.id.cancel);
        final EditText title = (EditText)findViewById(R.id.title), content = (EditText)findViewById(R.id.content);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("zl_debug", "click_save");
                String note_title = title.getText().toString(), note_content = content.getText().toString();
                Log.v("zl_debug_note_title", note_title);
                Log.v("zl_debug_note_content", note_content);
                Boolean res = save_note(note_title, note_content, 0, 0);
                if(res == true){
                    Toast.makeText(getApplicationContext(), "新建成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "新建失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public Boolean save_note(String title, String content, int type, int id){
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase db = null;
        db = database.getReadableDatabase();
        ContentValues cv = new ContentValues();
        if(type == 0){
            cv.put("note_title", title);
            cv.put("note_content", content);
            cv.put("note_status", "0");
            db.insert("note_list", null, cv);
            return true;
        }else{
            return false;
        }
//        return false;
    }
}
