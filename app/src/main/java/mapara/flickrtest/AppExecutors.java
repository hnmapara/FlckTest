package mapara.flickrtest;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    Executor mDiskIo;
    Executor mNetworkIo;
    Executor mMainThread;

    public AppExecutors() {
        mDiskIo = Executors.newSingleThreadExecutor();
        mNetworkIo = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        mMainThread = new MainThreadExecutor();
    }

    static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
