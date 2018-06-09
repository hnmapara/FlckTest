package mapara.flickrtest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.R;

/**
 * Created by mapara on 2/26/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<PhotoModel> mList;
    private Context mContext;

    public PhotoAdapter(@NonNull Context ctx, @NonNull List<PhotoModel> photoModelList) {
        mList = photoModelList;
        mContext = ctx;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.thumbnail_view, parent, false);
            return new PhotoViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof PhotoViewHolder) {
            PhotoModel photo = mList.get(position);
            Glide.with(mContext).load(photo.getUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                    .into(((PhotoViewHolder)holder).mImage);
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;

        public PhotoViewHolder(View v) {
            super(v);
            mImage = (ImageView)v;
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
