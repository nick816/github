package fivestar.starnotes;

import android.app.ActionBar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Rahman Younus(FiveStar)
 * StarNotes EditNoteActivity Class
 * EditNoteActivity shown after clicking on an Note and clicking on edit within the ActionBar.*
 */

public class EditNoteActivity extends ActionBarActivity {

    private NotesDatabase db_con;
    private long edit_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.edit_note);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3c699b));
        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey("entry_id")) {
            edit_id = extras.getLong("entry_id");
        } else {
            edit_id = -1;
            Toast.makeText(getApplicationContext(),
                    this.getString(R.string.intent_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu edit_note) {
        this.getMenuInflater().inflate(R.menu.edit_note, edit_note);
        return super.onCreateOptionsMenu(edit_note);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveEdit:
                final EditText headline = (EditText) this.findViewById(R.id.edit_headline);
                String headline_str = headline.getText().toString();
                final EditText content = (EditText) this.findViewById(R.id.edit_content);
                String content_str = content.getText().toString();
                if (headline_str.length() > 0 && content_str.length() > 0) {
                    SQLiteDatabase db = db_con.getWritableDatabase();
                    SQLiteStatement update_note = db.compileStatement(
                            "UPDATE entry " +
                                    "SET headline = ?, content = ?, edit_date = datetime('now', 'localtime') " +
                                    "WHERE id = ?");
                    update_note.bindString(1, headline_str);
                    update_note.bindString(2, content_str);
                    update_note.bindLong(3, edit_id);
                    update_note.execute();
                    db.close();
                    this.finish();
                } else {
                    Toast.makeText(this, "Fill out all Fields!", Toast.LENGTH_SHORT).show();
                }

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void cancelEdit(View view) {
        this.finish();
    }

    private void displayEdit() {
        EditText headline = (EditText) this.findViewById(R.id.edit_headline);
        EditText content = (EditText) this.findViewById(R.id.edit_content);
        SQLiteDatabase db = db_con.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT headline, content FROM entry WHERE id = ?",
                    new String[]{edit_id + ""});
            if (c.moveToNext()) {
                headline.setText(c.getString(0));
                content.setText(c.getString(1));
            }
        } finally {
            c.close();
            db.close();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (edit_id != -1) {
            db_con = new NotesDatabase(getApplicationContext());
            displayEdit();
        }
    }

    @Override
    protected void onPause() {
        db_con.close();
        super.onPause();
    }

}
