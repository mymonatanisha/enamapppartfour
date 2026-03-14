package com.enamnotes.enamapppartfour;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // 🔹 DB Info
    private static final String DATABASE_NAME = "participantdb";
    private static final int DATABASE_VERSION = 4;

    // 🔹 Example Table & Columns
    public static final String TABLE_NAME = "participantlist";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    // Step 2: Add GENDER column
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_DOB = "dob";

     public static final String COLUMN_OCCUPATION = "occupation";


    // 🔹 Create Table SQL
    private static final String QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_GENDER + " TEXT, " +
                    COLUMN_DOB + " TEXT, " +
                    COLUMN_OCCUPATION + " TEXT)";

    // Constructor
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called only once → when DB is created
        db.execSQL(QUERY);
    }

    // now creating a method to save data to database

public void addParticipant(String participantName, String gender, String dob,  String occupation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, participantName);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_DOB, dob );
        values.put(COLUMN_OCCUPATION, occupation);
        db.insert(TABLE_NAME, null, values);

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when DB version changes (drop old → create new)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
