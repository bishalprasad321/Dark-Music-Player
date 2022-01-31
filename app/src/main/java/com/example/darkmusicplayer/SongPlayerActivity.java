package com.example.darkmusicplayer;

import static com.example.darkmusicplayer.MainActivity.musicFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.darkmusicplayer.models.MusicFiles;

import java.util.ArrayList;

public class SongPlayerActivity extends AppCompatActivity {

    TextView nowPlayingText, songPlaying, durationStart, durationEnd, artistName;
    ImageButton mediaEventController, back, shuffle, repeat, skipPrevious, skipNext;

    SeekBar seekbar;
    ImageView songArt;

    int position = -1;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    Uri uri;
    static MediaPlayer mediaPlayer;

    private Handler handler = new Handler();

    private Thread playThread, nexThread, previousThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_player);

        initViews();
        getIntentMethod();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nowPlayingText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/danube.ttf"));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser)
                {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SongPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                    seekbar.setProgress(mCurrentPosition);
                    durationStart.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });

        songPlaying.setText(listSongs.get(position).getTitle());
        songPlaying.setSelected(true);
        artistName.setText(listSongs.get(position).getArtist());

    }

    @Override
    protected void onPostResume() {
        playThreadBtn();
        nextThreadBtn();
        previousThreadBtn();
        super.onPostResume();
    }

    private void nextThreadBtn() {
        nexThread = new Thread(){
            @Override
            public void run() {
                super.run();
                skipNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextTrigerred();
                    }
                });
            }
        };
        nexThread.start();
    }

    private void nextTrigerred() {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(this, uri);
            metaData(uri);
            songPlaying.setText(listSongs.get(position).getTitle());
            artistName.setText(listSongs.get(position).getArtist());
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaEventController.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }

        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(this, uri);
            metaData(uri);
            songPlaying.setText(listSongs.get(position).getTitle());
            artistName.setText(listSongs.get(position).getArtist());
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaEventController.setImageResource(R.drawable.ic_play);
        }
    }

    private void previousThreadBtn() {
        previousThread = new Thread(){
            @Override
            public void run() {
                super.run();
                skipPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        previousTrigerred();
                    }
                });
            }
        };
        previousThread.start();
    }

    private void previousTrigerred() {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listSongs.size() - 1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(this, uri);
            metaData(uri);
            songPlaying.setText(listSongs.get(position).getTitle());
            artistName.setText(listSongs.get(position).getArtist());
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaEventController.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }

        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) % listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(this, uri);
            metaData(uri);
            songPlaying.setText(listSongs.get(position).getTitle());
            artistName.setText(listSongs.get(position).getArtist());
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaEventController.setImageResource(R.drawable.ic_play);
        }
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                mediaEventController.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPauseTrigerred();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseTrigerred() {
        if (mediaPlayer.isPlaying())
        {
            mediaEventController.setImageResource(R.drawable.ic_play);
            mediaPlayer.pause();
            seekbar.setMax(mediaPlayer.getDuration() / 1000);
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }

        else
        {
            mediaEventController.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
            SongPlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                        seekbar.setProgress(mCurrentPosition);
                        durationStart.setText(formattedTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    private String formattedTime(int mCurrentPosition){
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        String totalOut = minutes + ":" + seconds;
        String totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1)
        {
            return totalNew;
        }
        else
        {
            return totalOut;
        }
    }

    private void getIntentMethod() {

        position = getIntent().getIntExtra("position", -1);
        listSongs = musicFiles;
        if (listSongs != null)
        {
            mediaEventController.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekbar.setMax(mediaPlayer.getDuration()/1000);
        metaData(uri);

    }

    private void initViews(){
        nowPlayingText = findViewById(R.id.nowPlaying);
        durationStart = findViewById(R.id.durationStart);
        durationEnd = findViewById(R.id.durationEnd);
        artistName = findViewById(R.id.artistName);
        songPlaying = findViewById(R.id.song_playing);
        mediaEventController = findViewById(R.id.mediaEventController);
        back = findViewById(R.id.backBtn);
        shuffle = findViewById(R.id.shuffle);
        repeat = findViewById(R.id.repeat);
        skipNext = findViewById(R.id.skipNext);
        skipPrevious = findViewById(R.id.skipPrevious);
        seekbar = findViewById(R.id.seekBar);
        songArt = findViewById(R.id.songArt);
    }

    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
        durationEnd.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if (art != null)
        {
            Glide.with(this).asBitmap().load(art).into(songArt);
        }
        else
        {
            Glide.with(this).asBitmap().load(R.drawable.ic_music_note).into(songArt);
        }
    }
}