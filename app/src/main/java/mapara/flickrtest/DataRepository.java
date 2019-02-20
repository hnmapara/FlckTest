package mapara.flickrtest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import java.util.List;
import java.util.Random;

import mapara.flickrtest.Model.PhotoModel;

public class DataRepository {

    private static DataRepository sInstance;
    private MutableLiveData<List<PhotoModel>> mMutableObservableProducts;
    private MutableLiveData<String> mMutableError;
    private AppExecutors mAppExecutors;
    private Random random = new Random();

    public static DataRepository getInstance(AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(appExecutors);
                }
            }
        }
        return sInstance;
    }

    private DataRepository(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
        mMutableObservableProducts = new MutableLiveData<>();
        mMutableError = new MutableLiveData<>();
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<PhotoModel>> getProducts() {
        return mMutableObservableProducts;
    }

    public LiveData<String> getObservableError() {
        return mMutableError;
    }

    public void fetchProducts(@NonNull String keyword, int page) {
        mAppExecutors.mNetworkIo.execute(() -> {
            List<PhotoModel> products = PhotoClient.getInstance().fetchPhotos(keyword, page);
            mMutableObservableProducts.postValue(products);
            int val = random.nextInt(10);
            if (val %3 == 0) {
                mMutableError.postValue("Su thayu la");
            }
        });
    }
}
