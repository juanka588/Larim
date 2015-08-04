package com.unal.larim.LN;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.unal.larim.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LinnaeusDatabase extends SQLiteOpenHelper {

    public static String DATABASE_PATH;
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "larim2016.sqlite";
    private final Context dbContext;
    public SQLiteDatabase dataBase;

    public LinnaeusDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        /*
         * desde carpetas externas File f=new File(path) if extits f.mkdir();
		 */
        this.dbContext = context;
        DATABASE_PATH = "/data/data/com.unal.larim/databases/";
        // checking database and open it if exists
        if (checkDataBase()) {
            openDataBase();
        } else {
            try {
                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database " + e.toString());
            }
            Toast.makeText(context, context.getText(R.string.database),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = dbContext.getResources().openRawResource(
                R.raw.larim2016);
        // dbContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        dataBase = SQLiteDatabase.openDatabase(dbPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            Log.v("db log", "La Base de Datos no existe");
        }
        if (checkDB != null) {
            exist = true;
            checkDB.close();
        }
        return exist;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}