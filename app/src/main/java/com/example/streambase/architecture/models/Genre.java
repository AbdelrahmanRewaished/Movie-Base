package com.example.streambase.architecture.models;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "streamGenres", primaryKeys = {"genre_id", "stream_id"},
        foreignKeys = {@ForeignKey(entity = Stream.class,
                    parentColumns = "id",
                    childColumns = "stream_id",
                    onDelete = ForeignKey.CASCADE)})

public class Genre {

    private int genre_id;
    private int stream_id;

    public Genre(int genre_id, int stream_id) {
        this.genre_id = genre_id;
        this.stream_id = stream_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public int getStream_id() {
        return stream_id;
    }

    public static String getGenre(int id) {
        switch(id) {
            case 28: return "Action";
            case 12: return "Adventure";
            case 16: return "Animation";
            case 35: return "Comedy";
            case 80: return "Crime";
            case 99: return "Documentary";
            case 18: return "Drama";
            case 10751: return "Family";
            case 14: return "Fantasy";
            case 36: return "History";
            case 27: return "Horror";
            case 10402: return "Music";
            case 9648: return "Mystery";
            case 10749: return "Romance";
            case 878: return "Science Fiction";
            case 10770: return "TV Movie";
            case 53: return "Thriller";
            case 10752: return "War";
            case 37: return "Western";
            default: return "";
        }
    }

}
