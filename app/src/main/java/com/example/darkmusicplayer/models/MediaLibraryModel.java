package com.example.darkmusicplayer.models;

public class MediaLibraryModel {

    int image;
    String musicName, singerName, duration;

    public MediaLibraryModel(int image, String musicName, String singerName, String duration) {
        this.image = image;
        this.musicName = musicName;
        this.singerName = singerName;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }
}
