package com.example.darkmusicplayer.models;

import android.view.View;

public class MusicFiles {

    private String path;
    private String artist;
    private String duration;
    private String title;
    private String album;

    public MusicFiles(String path, String artist, String duration, String title, String album) {
        this.path = path;
        this.artist = artist;
        this.duration = duration;
        this.title = title;
        this.album = album;
    }

    public MusicFiles() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public int getAnimation(){
        if (View.VISIBLE == 1)
        {
            return View.GONE;
        }

        else if (View.VISIBLE != 1)
        {
            return View.VISIBLE;
        }

        return View.GONE;
    }

}
