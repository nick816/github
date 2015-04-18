package fivestar.starnotes;

import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rahman Younus(FiveStar)
 * StarNotes NewNoteActivity Class
 * Class to create a new Note.*
 */
public class NewNoteActivity extends ActionBarActivity {

    private NotesDatabase db_con;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3c699b));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu new_note) {
        this.getMenuInflater().inflate(R.menu.new_note, new_note);
        return super.onCreateOptionsMenu(new_note);
    }

    @Override
    protected void onPause() {
        db_con.close();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        db_con = new NotesDatabase(getApplicationContext());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                final EditText headline = (EditText) this.findViewById(R.id.headline);
                String headline_str = headline.getText().toString();
                final EditText content = (EditText) this.findViewById(R.id.content);
                String content_str = content.getText().toString();
                if (headline_str.length() > 0 && content_str.length() > 0) {
                    SQLiteDatabase db = db_con.getWritableDatabase();
                    try {
                        SQLiteStatement inset_new = db.compileStatement(
                                "Insert into entry (headline, content, edit_date)" +
                                        "values (?,?,datetime('now', 'localtime'))");
                        inset_new.bindString(1, headline_str);
                        inset_new.bindString(2, content_str);
                        long id = inset_new.executeInsert();
                        Intent i = new Intent(getApplicationContext(), ShowNoteActivity.class);
                        i.putExtra("entry_id", id);
                        this.startActivity(i);
                        this.finish();
                    } finally {
                        db.close();
                    }
                } else {
                    Toast.makeText(this, "Fill out all Fields!", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
