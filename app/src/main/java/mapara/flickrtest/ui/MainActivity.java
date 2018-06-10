package mapara.flickrtest.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            ProductListFragment listFragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment, ProductListFragment.TAG).commit();
        }
    }

    public void show(@NonNull PhotoModel product) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ProductFragment.KEY_PRODUCT_URL,product.getUrl());
        productFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container, productFragment, ProductFragment.TAG)
                .commit();
    }
}
