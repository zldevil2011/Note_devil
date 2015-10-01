package com.note.note_devil;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNoteList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getNoteList(){
        TabHost tab = getTabHost();
        Intent intent_note_list = new Intent(this, note_list.class);
        Intent intent_note_todo = new Intent(this,note_todo.class);
        Intent intent_note_finished = new Intent(this,note_finished.class);
        Intent intent_note_edit = new Intent(this,note_edit.class);

        tab.addTab(tab.newTabSpec("All").setIndicator("All", null).setContent(intent_note_list));
        tab.addTab(tab.newTabSpec("Todo").setIndicator("Todo", null).setContent(intent_note_todo));
        tab.addTab(tab.newTabSpec("Finished").setIndicator("Finished", null).setContent(intent_note_finished));
        tab.addTab(tab.newTabSpec("Edit").setIndicator("Edit", null).setContent(intent_note_edit));
        tab.setCurrentTab(0);
    }
}
