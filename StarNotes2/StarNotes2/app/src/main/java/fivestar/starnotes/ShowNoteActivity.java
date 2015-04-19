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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rahman Younus(FiveStar)
 * StarNotes ShowNoteActivity Class
 * Displays Note when clicked on from Main Activity*
 */

public class ShowNoteActivity extends ActionBarActivity {

    private NotesDatabase db_con;

    private long displayed_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_note);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff3c699b));
        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey("entry_id")) {
            displayed_id = extras.getLong("entry_id");
        } else {
            displayed_id = -1;
            Toast.makeText(getApplicationContext(),
                    this.getString(R.string.intent_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (displayed_id != -1) {
            db_con = new NotesDatabase(getApplicationContext());
            displayContent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.display_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
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
                                        del_curr.bindLong(1, displayed_id);
                                        del_curr.execute();
                                        db.close();
                                        ShowNoteActivity.this.finish();
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
                return true;
            case R.id.menu_edit:
                Intent i = new Intent(this, EditNoteActivity.class);
                i.putExtra("entry_id", displayed_id);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayContent() {
        final TextView headline = (TextView) this.findViewById(R.id.display_headline);
        final TextView date = (TextView) this.findViewById(R.id.display_date);
        final TextView content = (TextView) this.findViewById(R.id.display_content);
        SQLiteDatabase db = db_con.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.rawQuery(
                    "SELECT headline, edit_date, content " +
                            "FROM entry " +
                            "WHERE id = ?",
                    new String[]{displayed_id + ""}
            );
            if (c.moveToNext()) {
                headline.setText(c.getString(0));
                date.setText(c.getString(1) + " ");
                content.setText(c.getString(2));
            }
        } finally {
            c.close();
            db.close();
        }
    }

    @Override
    protected void onPause() {
        db_con.close();
        super.onPause();
    }

}
