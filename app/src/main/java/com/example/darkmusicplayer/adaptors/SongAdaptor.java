package com.example.darkmusicplayer.adaptors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.darkmusicplayer.R;
import com.example.darkmusicplayer.SongPlayerActivity;
import com.example.darkmusicplayer.models.MusicFiles;

import java.util.ArrayList;

public class SongAdaptor extends RecyclerView.Adapter<SongAdaptor.viewHolder> {

    ArrayList<MusicFiles> musics;
    Context context;

    public SongAdaptor(ArrayList<MusicFiles> musics, Context context) {
        this.musics = musics;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_playing, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.musicName.setText(musics.get(position).getTitle());

        holder.singerName.setText(musics.get(position).getArtist());

        byte[] art = getAlbumArt(musics.get(position).getPath());
        if (art != null) {
            Glide.with(context).asBitmap().load(art).into(holder.imageView);
        } else {
            Glide.with(context).load(R.drawable.ic_music_note).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView musicName, singerName;
        LottieAnimationView animation;

        public viewHolder(@NonNull final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            musicName = itemView.findViewById(R.id.songName);
            singerName = itemView.findViewById(R.id.artistName);
            animation = itemView.findViewById(R.id.animation);
        }
    }

    public byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}

