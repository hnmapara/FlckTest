package mapara.flickrtest.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Trace;

import androidx.appcompat.widget.Toolbar;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.PhotoClient;
import mapara.flickrtest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Trace.beginSection("FLKTESTACTIVITY_ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            ProductListFragment listFragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, listFragment, ProductListFragment.TAG).commit();
        }
        PhotoClient.getInstance().setMemoryLeak(this);
        Trace.endSection();
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
