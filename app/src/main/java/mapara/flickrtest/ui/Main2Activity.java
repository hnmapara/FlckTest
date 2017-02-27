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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mapara.flickrtest.PhotoClient;
import mapara.flickrtest.Presenter;
import mapara.flickrtest.R;
import mapara.flickrtest.util.Util;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotoAdapter mAdapter;
    private static Presenter mPresenter;
    private boolean mIsLoading;

    private int visibleThreshold = 8;
    private int lastVisibleItem, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mPresenter == null) {
            mPresenter = new Presenter(this);
        }
        if (savedInstanceState != null) {
            mPresenter.setContext(this);
        }

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
        if (PhotoClient.getInstance().getPhotos().size() == 0) {
            mPresenter.fetchShowNextPage();
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = ((GridLayoutManager)mLayoutManager).findLastCompletelyVisibleItemPosition();

                if (!mIsLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.loadMore();
                        }
                    });
                    mIsLoading = true;
                } else {
                    Log.e(TAG, "will not call loadmore");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) {
            mPresenter.cancel();
            mPresenter = null;
        }
    }

    public void refreshList() {
        mIsLoading = false;
        mAdapter.notifyDataSetChanged();
    }

    public void showLoadingView() {
        mAdapter.notifyItemInserted(PhotoClient.getInstance().getPhotos().size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
