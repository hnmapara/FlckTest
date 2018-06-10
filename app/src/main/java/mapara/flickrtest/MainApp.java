package mapara.flickrtest;

import android.app.Application;

public class MainApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }
}
