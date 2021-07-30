package com.example.darkmusicplayer.adaptors;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.darkmusicplayer.R;
import com.example.darkmusicplayer.models.MediaLibraryModel;
import com.example.darkmusicplayer.models.MusicFiles;
import com.example.darkmusicplayer.models.SongModel;

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
    public void onBindViewHolder(@NonNull SongAdaptor.viewHolder holder, int position) {

        holder.musicName.setText(musics.get(position).getTitle());

        holder.singerName.setText(musics.get(position).getArtist());

        byte[] art = getAlbumArt(musics.get(position).getPath());
        if (art != null)
        {
            Glide.with(context).asBitmap().load(art).into(holder.imageView);
        }
        else
        {
            Glide.with(context).load(R.drawable.musicart).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView musicName, singerName, duration;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            musicName = itemView.findViewById(R.id.songName);
            singerName = itemView.findViewById(R.id.artistName);
        }
    }

    public byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte [] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
