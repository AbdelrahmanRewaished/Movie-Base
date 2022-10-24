package com.example.moviesapp.architecture.caching;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviesapp.architecture.models.Movie;
import com.example.moviesapp.architecture.models.Stream;
import com.example.moviesapp.architecture.models.TVSeries;

import java.io.File;

@androidx.room.Database(entities = {Movie.class, TVSeries.class}, version = 1)
public abstract class Database extends RoomDatabase{

    private static volatile Database database;

    public static boolean DatabaseExists(Context context) {
        File dbFile = context.getDatabasePath("streams_database");
        return dbFile.exists();
    }

    public abstract Dao dao();

    public static synchronized Database getDatabase(final Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), Database.class, "streams_database")
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }

}
