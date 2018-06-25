package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btandjaja.www.popular_movies_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    /* declarations */
    private Context mContext;
    private static MovieAdapterOnClickHandler mClickHandler;
    private static ArrayList<Movie> mMovieList;

    /**
     * Creates an empty MovieAdapter
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) { mClickHandler = clickHandler; }

    /**
     * This method is used to set the movies on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web or sorted data
     * but don't want to create a new MovieAdapter to display it.
     *
     * @param movieList The new movie data to be displayed.
     */
    public void setMovieList(Context context, ArrayList<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
        notifyDataSetChanged();
    }

    /* interface for onClick */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /* MovieViewHolder */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImageView;

        private MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.single_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            /* get data from the right position */
            Movie movie = Movie.copyMovie(mMovieList.get(getAdapterPosition()));
            if ( movie == null ) return;
            /* create movie object */
            mClickHandler.onClick(movie);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new MovieAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieViewHolder The ViewHolder which should be updated to represent the
     *                        contents of the item at the given position in the data set.
     * @param position        The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovieList.get(position);
        if(movie == null) return;
        String path = movie.getPosterPath();
        Picasso.with(mContext).load(path).into(movieViewHolder.mImageView);
    }

    /**
     * This method returns the number of elements in table if there's data
     * or else it returns 0 if mCursor is null.
     *
     * @return elements in SQLite table
     */
    @Override
    public int getItemCount() { return mMovieList == null ? 0 : mMovieList.size(); }
}