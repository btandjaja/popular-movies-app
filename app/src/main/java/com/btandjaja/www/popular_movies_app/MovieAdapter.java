package com.btandjaja.www.popular_movies_app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private final Context mContext;
    private static ArrayList<Movie> mMovieList;
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, ArrayList<Movie> movieList) {
        mContext = (Context) clickHandler;
        mClickHandler = clickHandler;
//        MovieUtils.copy(movieList, mMovieList);
        mMovieList = movieList;
    }

    //TODO change
    /**
     * This method is used to set the movies on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param newMovieList The new movie data to be displayed.
     */
    public void setWeatherData(ArrayList<Movie> newMovieList) {
        mMovieList = newMovieList;
        notifyDataSetChanged();
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.single_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            Movie movie = mMovieList.get(getAdapterPosition());
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
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = mMovieList.get(position);
        String path = IMAGE_URL + movie.getPosterPath();
//        ImageView imageView = movieViewHolder.mImageView;
        Picasso.with(mContext).load(path).into(movieViewHolder.mImageView);
//        mContext.
    }

    @Override
    public int getItemCount() {
        return mMovieList.size() == 0 ? 0 : mMovieList.size();
    }

//    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
//        mContext = context;
//        mMovieList = movieList;
//    }
//
//    @Override
//    public int getCount() {
//        Log.v("***", "trying to get size");return mMovieList.size(); }
//
//    /* do we need this? */
//    @Override
//    public Object getItem(int position) { return mMovieList.get(position); }
//
//    /* don't know what this is for */
//    @Override
//    public long getItemId(int position) { return 0; }
//
//    /* check implementation */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView = convertView == null ?
//                new ImageView(mContext) : (ImageView) convertView;
//
//        String imagePath = IMAGE_URL + mMovieList.get(position).getPosterPath();
//
//        Picasso.with(mContext).load(imagePath).into(imageView);
//
//        return imageView;
//    }
}
