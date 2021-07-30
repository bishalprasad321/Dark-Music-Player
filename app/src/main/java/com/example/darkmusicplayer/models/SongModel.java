package com.example.darkmusicplayer.models;

public class SongModel {

    int albumArt;
    String songName, duration, artistName;

    public SongModel(int albumArt, String songName, String artistName, String duration) {
        this.albumArt = albumArt;
        this.songName = songName;
        this.artistName = artistName;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getAlbumArt() {
        return albumArt;
    }

    public String getSongName() {
        return songName;
    }

    public void setAlbumArt(int albumArt) {
        this.albumArt = albumArt;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
