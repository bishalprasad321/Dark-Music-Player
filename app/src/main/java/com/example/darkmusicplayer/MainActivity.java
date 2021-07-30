package com.example.darkmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.darkmusicplayer.adaptors.SongAdaptor;
import com.example.darkmusicplayer.models.MusicFiles;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView nowPlaying, currentSong, mediaLibrary;
    RecyclerView recyclerView;

    SongAdaptor musicAdaptor;

    public static ArrayList<MusicFiles> musicFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nowPlaying = findViewById(R.id.musicNametextView);
        recyclerView = findViewById(R.id.recyclerViewMain);

        Typeface title = Typeface.createFromAsset(getAssets(), "fonts/danube.ttf");
        nowPlaying.setTypeface(title);

        mediaLibrary = findViewById(R.id.mediaLibraryTextView);
        mediaLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MediaLibraryAcitivty.class);
                startActivity(intent);
            }
        });

        currentSong = findViewById(R.id.currentSong);
        currentSong.setSelected(true);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        musicFiles = fetchSongs(MainActivity.this);

                        if (!(musicFiles.size() < 1))
                        {
                            musicAdaptor = new SongAdaptor(musicFiles, getApplicationContext());
                            recyclerView.setAdapter(musicAdaptor);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    public static ArrayList<MusicFiles> fetchSongs(Context context) {
        ArrayList<MusicFiles> songList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(0);
                String artist = cursor.getString(1);
                String duration = cursor.getString(2);
                String title = cursor.getString(3);
                String album = cursor.getString(4);

                MusicFiles musicFiles = new MusicFiles(path, artist, duration, title, album);

                // log.e
                Log.e("Path" + path, "Album" + album);

                songList.add(musicFiles);
            }
            cursor.close();
        }

        return songList;
    }

}