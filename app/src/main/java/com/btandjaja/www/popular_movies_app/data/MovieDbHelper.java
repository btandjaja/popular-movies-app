package com.btandjaja.www.popular_movies_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.btandjaja.www.popular_movies_app.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {
    /* database name and version */
    private static final String DATABASE_NAME = "movielist.db";
    private static final int DATABASE_VERSION = 1;

    /* place holder for new column */
    private static final String NEW_COLUMN = "place_holder";

    /* constructor */
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** This method creates empty table with
     *  _ID is column 0
     *  COLUMN_NAME_TITLE is column 1
     *  COLUMN_NAME_OVER_VIEW is column 2
     *  COLUMN_NAME_POPULARITY is column 3
     *  COLUMN_NAME_VOTE_AVERAGE is column 4
     *  COLUMN_NAME_RELEASE_DATE is column 5
     *  COLUMN_NAME_POSTER_PATH is column 6
     * @param sqLiteDatabase    table to be created to database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIELIST_TABLE = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_NAME_OVER_VIEW + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_NAME_POPULARITY + " REAL NOT NULL, " +
                MovieEntry.COLUMN_NAME_VOTE_AVERAGE + " REAL NOT NULL, " +
                MovieEntry.COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_NAME_POSTER_PATH + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_NAME_MOVIE_ID + " INT NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /* check for new version */
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("ALTER TABLE " + MovieEntry.TABLE_NAME +
                    " ADD COLUMN " + NEW_COLUMN + " INTEGER DEFAULT 0");
        }
        /* if table exist, replace it */
        else {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
