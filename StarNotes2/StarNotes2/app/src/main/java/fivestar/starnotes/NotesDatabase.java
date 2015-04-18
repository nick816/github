package fivestar.starnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rahman Younus(FiveStar)
 * StarNotes NotesDatabase Class
 * SQLite Helper*
 */
public class NotesDatabase extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "notebook.db";

    private final static int DATABASE_VERSION = 3;

    public NotesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table entry(" +
                "headline TEXT," +
                "content TEXT," +
                "edit_date DATE," +
                "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE entry");
        onCreate(db);
    }

}