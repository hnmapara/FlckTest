package mapara.flickrtest;

import android.app.Application;
import android.os.Trace;

public class MainApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        Trace.beginSection("MAINNAPP_ONCREATE");
        super.onCreate();
        mAppExecutors = new AppExecutors();
        Trace.endSection();
    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }
}
