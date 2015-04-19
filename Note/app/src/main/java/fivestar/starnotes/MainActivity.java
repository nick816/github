package fivestar.starnotes;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Rahman Younus(FiveStar)
 * StarNotes MainActivity Class
 * Main Activity connects to NotesDatabase*
 */
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private NotesDatabase db_con;

    private ListView notesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesList = (ListView) findViewById(android.R.id.list);
        notesList.setOnItemClickListener(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3c699b));

        //ListView lv = (ListView)findViewById(android.R.id.list);
        ImageView empty = (ImageView)findViewById(android.R.id.empty);
        notesList.setEmptyView(empty);


      //  ImageView empty = new ImageView(this);
      //  empty.setImageResource(R.drawable.emptydb);
      //  empty.setVisibility(View.VISIBLE);
      //  notesList.setEmptyView(empty);
      //  this.registerForContextMenu(notesList);
    }

    @Override
    public void onStart() {
        super.onStart();
        db_con = new NotesDatabase(this);
        listNotes();
    }

    private void listNotes() {
        SQLiteDatabase db = db_con.getReadableDatabase();
        try {
            Cursor notesCursor = db.rawQuery("SELECT headline, strftime( ?, edit_date) as 'date', id as '_id' " +
                            "FROM entry " +
                            "ORDER BY edit_date DESC",
                    new String[]{this.getString(R.string.date_format)});

            if (notesCursor == null) {
                notesCursor.moveToFirst();
                if (notesCursor.getInt(0) == 0) {
                    ImageView empty = new ImageView(this);
                    empty.setImageResource(R.drawable.emptydb);
                    empty.setVisibility(View.VISIBLE);
                }

            }

            final ListAdapter noteAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2, notesCursor,
                    new String[]{"headline", "date"},
                    new int[]{android.R.id.text1, android.R.id.text2});

            notesList.setAdapter(noteAdapter);
        } finally {
            db.close();
        }
    }

    @Override
    protected void onPause() {
        db_con.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // Load the Context Menu:
        this.getMenuInflater().inflate(R.menu.main_longpress_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create:
                Intent i = new Intent(getApplicationContext(), NewNoteActivity.class);
                this.startActivity(i);
                return true;
            case R.id.menu_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.d("OnlyLog", "ID: " + info.id);
        switch (item.getItemId()) {
            case R.id.main_longpress_show:
                Intent show = new Intent(this, ShowNoteActivity.class);
                show.putExtra("entry_id", info.id);
                this.startActivity(show);
                return true;
            case R.id.main_longpress_edit:
                Intent edit = new Intent(this, EditNoteActivity.class);
                edit.putExtra("entry_id", info.id);
                this.startActivity(edit);
                return true;
            case R.id.main_longpress_delete:
                this.longpressDelete(info.id);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void longpressDelete(final long note_id) {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage(this.getString(R.string.delete_current_dialog))
                .setCancelable(true)
                .setPositiveButton(this.getString(R.string.delete_current_dialog_yes),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SQLiteDatabase db = db_con.getWritableDatabase();
                                SQLiteStatement del_curr = db.compileStatement(
                                        "DELETE FROM entry WHERE id = ?");
                                del_curr.bindLong(1, note_id);
                                del_curr.execute();
                                db.close();
                                MainActivity.this.listNotes();
                            }
                        })
                .setNegativeButton(this.getString(R.string.delete_current_dialog_no),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog dialog = build.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, ShowNoteActivity.class);
        i.putExtra("entry_id", id);
        this.startActivity(i);
    }
}