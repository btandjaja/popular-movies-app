package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btandjaja.www.popular_movies_app.R;
import com.btandjaja.www.popular_movies_app.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    /* declarations */
    private static Context mContext;
    private static Cursor mCursor;
    private static MovieAdapterOnClickHandler mClickHandler;

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler, Cursor cursor) {
        mContext = (Context) clickHandler;
        mClickHandler = clickHandler;
        mCursor = cursor;
    }

    //TODO remove
    /**
     * This method is used to set the movies on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web or sorted data
     * but don't want to create a new MovieAdapter to display it.
     *
     * @param cursor The new movie data to be displayed.
     */
    public void setMovieList(MovieAdapterOnClickHandler clickHandler, Cursor cursor) {
        mContext = (Context) clickHandler;
        mClickHandler = clickHandler;
        mCursor = cursor;
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
            /* move cursor to the right position */
            if (!mCursor.moveToPosition(getAdapterPosition())) return;
            /* create movie object */
            Movie movie = new Movie(mCursor);
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
     *                          contents of the item at the given position in the data set.
     * @param position        The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        if (!mCursor.moveToPosition(position)) return;
        String path = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH
        ));
        Picasso.with(mContext).load(path).into(movieViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
