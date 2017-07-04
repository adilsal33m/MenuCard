package com.adilsal33m.menucard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Adil Saleem on 6/27/2017.
 */
public class DataBaseHelper {

    private static final String DB_NAME = "menucard_database";

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public DataBaseHelper(Context context) {
        mContext = context;
    }


    public SQLiteDatabase openOrCreate() {
        File dbFile = mContext.getDatabasePath(DB_NAME);
        if (dbFile.exists()) {
            mContext.deleteDatabase(DB_NAME);
            copyDatabase(dbFile);
        }
        else
        {
            copyDatabase(dbFile);
        }
        mSQLiteDatabase = SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        return mSQLiteDatabase;
    }

    private void copyDatabase(File dbFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            mContext.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            is = mContext.getAssets().open(DB_NAME);
            os = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
