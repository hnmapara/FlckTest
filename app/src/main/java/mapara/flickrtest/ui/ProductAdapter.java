package mapara.flickrtest.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<PhotoModel> mProductList;
    private ProductClickCallback mClickCallback;
    public interface ProductClickCallback {
        void onClick(PhotoModel product);
    }

    public ProductAdapter(ProductClickCallback clickCallback) {
        mClickCallback = clickCallback;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_view, parent, false);
        final ProductViewHolder pdViewHolder =  new ProductViewHolder(view);
        view.setOnClickListener(v -> {
            mClickCallback.onClick(mProductList.get(pdViewHolder.getAdapterPosition()));
        });
        return pdViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PhotoModel photo = mProductList.get(position);
//        holder.textView.setText(photo.getId());

        Glide.with(holder.itemView.getContext()).load(photo.getUrl())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    public void setProductList(@NonNull final List<PhotoModel> productList) {
        if (mProductList == null) {
            mProductList = productList;
            notifyItemRangeInserted(0, productList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return productList.get(newItemPosition).getId().equals(mProductList.get(oldItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PhotoModel newModel = productList.get(newItemPosition);
                    PhotoModel oldModel = mProductList.get(oldItemPosition);
                    return newModel.getId().equals(oldModel.getId()) && newModel.getUrl().equals(oldModel.getUrl());
                }
            });

            result.dispatchUpdatesTo(this);
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.img);
//            textView =itemView.findViewById(R.id.text);
        }
    }
}
