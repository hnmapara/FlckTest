package mapara.flickrtest.ui;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.R;
import mapara.flickrtest.util.Util;

public class ProductListFragment extends Fragment {
    public static final String TAG = "ProductListFragment";
    private ProductAdapter mProductAdapter;
    private ProgressBar mProgressbar;
    private ProductListViewModel mListViewModel;

    private ProductAdapter.ProductClickCallback mClickCallback = new ProductAdapter.ProductClickCallback() {
        @Override
        public void onClick(PhotoModel product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(product);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Trace.beginSection("PRODUCTLIST_ONCREATEVIEW");
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        mProgressbar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerView = view.findViewById(R.id.products_list);
        mProductAdapter = new ProductAdapter(mClickCallback);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), Util.calculateNoOfColumns(getContext(), getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition == mProductAdapter.getItemCount() - 1) {
                    mListViewModel.fetchMore();
                }
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mProductAdapter);
        Trace.endSection();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListViewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        subscribeUi();
    }

    private void subscribeUi() {
        mListViewModel.getObservableListProducts().observe(this, new Observer<List<PhotoModel>>() {
            @Override
            public void onChanged(@Nullable List<PhotoModel> photoModels) {
                if (photoModels != null) {
                    mProgressbar.setVisibility(View.GONE);
                    mProductAdapter.setProductList(photoModels);
                } else {
                    mProgressbar.setVisibility(View.VISIBLE);
                }
            }
        });

        mListViewModel.getObservableError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                Snackbar.make(getView(), error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
