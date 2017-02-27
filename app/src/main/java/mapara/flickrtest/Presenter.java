package mapara.flickrtest;

import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

import mapara.flickrtest.ui.Main2Activity;

/**
 * Created by mapara on 2/26/17.
 */

public class Presenter {
    private static final String TAG = "Presenter";
    Main2Activity mActivityRef;

    public Presenter(Main2Activity activity) {
        mActivityRef = activity;
        PhotoClient.getInstance().fetchAsyncPhotosForKeyword("Golden gate", 1, new PhotoClient.IOnFinish() {
            @Override
            public void onSuccess(boolean isSuccess) {
                refreshView();
            }
        });
    }

    public void setContext(@Nullable Main2Activity activity) {
        Log.d(TAG, "setContext" + activity);
       mActivityRef = activity;
    }

    private void refreshView() {
        //Main2Activity activity = mActivityRef.get();
        if (mActivityRef != null || !mActivityRef.isFinishing()) {
            mActivityRef.refreshList();
        }
    }

    public void cancel() {
        Log.d(TAG, "cancel");
        mActivityRef = null;
    }
}
