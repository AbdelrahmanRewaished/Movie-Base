package com.example.moviesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesapp.Movie;

import java.io.File;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase{

    private static volatile MovieDatabase movieDatabase;

    public static boolean DatabaseExists(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public abstract MovieDao movieDao();

    public static synchronized MovieDatabase getMovieDatabase(final Context context) {
        if(movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "movies_database")
                    .fallbackToDestructiveMigration().build();
        }
        return movieDatabase;
    }

}
