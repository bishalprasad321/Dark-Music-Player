package com.example.darkmusicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darkmusicplayer.fragments.AlbumFragment;
import com.example.darkmusicplayer.fragments.SongsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import static com.example.darkmusicplayer.MainActivity.musicFiles;

import static com.example.darkmusicplayer.MainActivity.fetchSongs;

import de.hdodenhof.circleimageview.CircleImageView;

public class MediaLibraryActivity extends AppCompatActivity {

    ImageView back;
    TextView currentSongPlaying;
    CircleImageView musicImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_library);

        currentSongPlaying = findViewById(R.id.currentSong2);
        currentSongPlaying.setSelected(true);
        currentSongPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaLibraryActivity.this, SongPlayerActivity.class);
                startActivity(intent);
            }
        });

        musicImage = findViewById(R.id.song_image_media_library);
        musicImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaLibraryActivity.this, SongPlayerActivity.class);
                startActivity(intent);
            }
        });

        back = findViewById(R.id.backImageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        initPageManager();

        musicFiles = fetchSongs(this);

    }

    private void initPageManager() {

        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPagerAdaptor viewPagerAdaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        viewPagerAdaptor.addFragment(new SongsFragment(), "All Songs");
        viewPagerAdaptor.addFragment(new AlbumFragment(),"Album");
        viewPager.setAdapter(viewPagerAdaptor);
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class ViewPagerAdaptor extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdaptor(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        void addFragment (Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}