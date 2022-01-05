package in.linus.busmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class user_sqlhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "busmate";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "login_details";

    public user_sqlhelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE login_details(user_id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT NOT NULL,password TEXT NOT NULL,email TEXT NOT NULL,created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,active TEXT NOT NULL DEFAULT 'A')");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS login_details");
        onCreate(sQLiteDatabase);
    }

    public void addUser(user user) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail_id());
        writableDatabase.insert(TABLE_USER, (String) null, contentValues);
        writableDatabase.close();
    }

    public void deleteUser(user user) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(TABLE_USER, "user_id = ?", new String[]{String.valueOf(user.getUserid())});
        writableDatabase.close();
    }
}
