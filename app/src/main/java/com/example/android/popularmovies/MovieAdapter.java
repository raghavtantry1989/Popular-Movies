package com.example.android.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tantryr on 25/10/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0 ,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }

        ImageView poster = (ImageView) convertView.findViewById(R.id.imageViewMoviePoster);
        Log.d("Something", movie.getPoster());
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w500/"+ movie.getPoster())
                .into(poster);

        return convertView;

    }
}
