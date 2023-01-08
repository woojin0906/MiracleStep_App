package kr.co.company.healthapplication.dbAll;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String UserIDSave = "UserID";

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
    }

    public void createTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + UserIDSave + "(id text)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
        }
    }

    public void insertUserID(SQLiteDatabase db, String id) {
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + UserIDSave + "values('" + id + "')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void DeleteUserID(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            String sql = "DELETE FROM " + UserIDSave;
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}