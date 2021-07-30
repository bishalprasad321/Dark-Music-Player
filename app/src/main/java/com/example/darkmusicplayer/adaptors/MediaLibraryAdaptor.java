package com.example.darkmusicplayer.adaptors;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.darkmusicplayer.models.MusicFiles;
import com.example.darkmusicplayer.R;

import java.util.ArrayList;

public class MediaLibraryAdaptor extends RecyclerView.Adapter<MediaLibraryAdaptor.viewHolder>{

    private ArrayList<MusicFiles> songList;
    private Context context;

    public MediaLibraryAdaptor(ArrayList<MusicFiles> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_lists, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.songText.setText(songList.get(position).getTitle());

        holder.artistText.setText(songList.get(position).getArtist());

        holder.durationText.setText(songList.get(position).getDuration());

        byte[] art = getAlbumArt(songList.get(position).getPath());
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
        return songList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imageView ;
        TextView songText, artistText, durationText;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.albumImage);
            songText = itemView.findViewById(R.id.musicName);
            artistText = itemView.findViewById(R.id.singerName);
            durationText = itemView.findViewById(R.id.durationText);
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
