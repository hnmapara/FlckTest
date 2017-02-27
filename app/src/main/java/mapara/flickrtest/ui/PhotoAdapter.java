package mapara.flickrtest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.R;

/**
 * Created by mapara on 2/26/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<PhotoModel> mList;
    private Context mContext;

    public PhotoAdapter(@NonNull Context ctx, @NonNull List<PhotoModel> photoModelList) {
        mList = photoModelList;
        mContext = ctx;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.thumbnail_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoModel photo = mList.get(position);
        Glide.with(mContext).load(photo.getUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;

        public ViewHolder(View v) {
            super(v);
            mImage = (ImageView)v;
        }
    }
}
