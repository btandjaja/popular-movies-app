package com.btandjaja.www.popular_movies_app.MovieAdapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.btandjaja.www.popular_movies_app.R;
import com.btandjaja.www.popular_movies_app.utilities.MovieUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private static TrailerOnClickHandler mClickHandler;
    private static Context mContext;
    private static ArrayList<String> mKeys;

    public TrailerAdapter(TrailerOnClickHandler clickHandler) { mClickHandler = clickHandler; }

    public void setTrailer(Context context, ArrayList<String> keys) {
        mKeys = keys;
        mContext = context;
        notifyDataSetChanged();
    }

    public interface TrailerOnClickHandler {
        void onClick(Uri uri);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mImageView;

        private TrailerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_trailer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String key = mKeys.get(getAdapterPosition());
            if (key == null || TextUtils.isEmpty(key)) return;
            String videoLink = mContext.getResources().getString(R.string.youtube) + key;
            mClickHandler.onClick(Uri.parse(videoLink));
        }
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_preview_image;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        if(mKeys.get(position) == null) return;
        String thumbNailPath = mContext.getString(R.string.youtube_schmeme_authority) +
                mKeys.get(position) + mContext.getString(R.string.youtube_jpg_query);
        Picasso.with(mContext).load(thumbNailPath).into(holder.mImageView);
    }

    /**
     * This method returns number of available keys
     * @return
     */
    @Override
    public int getItemCount() { return mKeys == null ? 0 : mKeys.size(); }
}
