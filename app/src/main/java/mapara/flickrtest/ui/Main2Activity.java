package mapara.flickrtest.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import mapara.flickrtest.PhotoClient;
import mapara.flickrtest.Presenter;
import mapara.flickrtest.R;
import mapara.flickrtest.util.Util;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotoAdapter mAdapter;
    private static Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, Util.calculateNoOfColumns(getApplicationContext(), getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size)));

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PhotoAdapter(this, PhotoClient.getInstance().getPhotos());
        mRecyclerView.setAdapter(mAdapter);
        if (mPresenter == null) {
            mPresenter = new Presenter(this);
        }
        if (savedInstanceState != null) {
            mPresenter.setContext(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Maintains the recyclerView scroll position even if activity is finished or recreated
        //PhotosManager.getInstance().setState(mLayoutManager.onSaveInstanceState());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (PhotosManager.getInstance() != null) {
        //    PhotosManager.getInstance().deRegister();
        //}
        if (!isChangingConfigurations()) {
            mPresenter.cancel();
            mPresenter = null;
        }
    }

    public void refreshList() {
        mAdapter.notifyDataSetChanged();
    }

}
