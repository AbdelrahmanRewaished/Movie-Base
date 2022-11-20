package com.example.streambase.architecture.caching;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.streambase.architecture.models.Genre;
import com.example.streambase.architecture.models.Movie;
import com.example.streambase.architecture.models.Stream;
import com.example.streambase.architecture.models.TVSeries;

@androidx.room.Database(entities = {Stream.class ,Movie.class, TVSeries.class, Genre.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase{

    private static volatile Database database;

    public abstract Dao dao();

    public static synchronized Database getDatabase(final Context context) {
        if(database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), Database.class, "streams_database")
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }

}
