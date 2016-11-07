package Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dream on 2016/4/25.
 */
public class SQLiteDataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "net_message.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS GAME_INFO(name TEXT PRIMARY KEY, value TEXT, time INTEGER)");
        Log.v("SQLiteDataBaseHelper", "the database has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
