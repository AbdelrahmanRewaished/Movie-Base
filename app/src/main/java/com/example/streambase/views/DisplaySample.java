package com.example.streambase.views;

public class DisplaySample {

    private String streamState;
    private String streamType;

    public DisplaySample(char streamType, String streamState) {
        this.streamState = streamState;
        this.streamType = streamType == 'M'? "MOVIES": "TV SHOWS";
    }

    // What will be passed to the api
    public String getStreamStateOriginal() {
        return streamState;
    }

    // What will be displayed
    public String getStreamState() {
        if(streamState.equals("latest"))
            return "Trending";
        return capitalizeFirstLetter(streamState);
    }

    public String getStreamType() {
        return streamType;
    }

    private String capitalizeFirstLetter(String s) {
        if(! s.contains("_"))
            return s.substring(0, 1).toUpperCase() + s.substring(1);

        String[] split = s.split("_");
        return split[0].substring(0, 1).toUpperCase() + split[0].substring(1) + " " + split[1];
    }
}
