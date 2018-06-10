package mapara.flickrtest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import mapara.flickrtest.R;

public class ProductFragment extends Fragment {

    public static final String TAG = "ProductFragment";

    public static final String KEY_PRODUCT_URL = "product_url";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.thumbnail_view, container, false);
        Glide.with(getContext()).load(getArguments().getString(KEY_PRODUCT_URL))
                .apply(new RequestOptions().placeholder(R.drawable.placeholder))
                .into((ImageView) view.findViewById(R.id.img));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
