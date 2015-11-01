package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        ImageView poster = (ImageView) view.findViewById(R.id.imageViewDetailMoviePoster);
        TextView title = (TextView) view.findViewById(R.id.textViewDetailTitle);
        TextView overview = (TextView) view.findViewById(R.id.textViewDetailOverview);
        TextView releaseDate = (TextView) view.findViewById(R.id.textViewDetailReleaseDate);
        TextView averageVote = (TextView) view.findViewById(R.id.textViewDetailAverageVote);

        Intent intent = getActivity().getIntent();

        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w500/"+ intent.getStringExtra("poster_url"))
                .into(poster);
        title.setText(intent.getStringExtra("title"));
        overview.setText(intent.getStringExtra("overview"));
        releaseDate.setText(intent.getStringExtra("release_date"));
        averageVote.setText(String.valueOf(intent.getDoubleExtra("vote_avg",0.0)));
    }
}
