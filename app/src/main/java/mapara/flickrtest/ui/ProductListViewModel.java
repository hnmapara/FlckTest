package mapara.flickrtest.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import mapara.flickrtest.DataRepository;
import mapara.flickrtest.MainApp;
import mapara.flickrtest.Model.PhotoModel;

public class ProductListViewModel extends AndroidViewModel {
    public int pageNum = 1;
    public String keyword = "Apple";

    private DataRepository mDataRepository;
    private LiveData<List<PhotoModel>> mObservableListProducts;
    private LiveData<String> mObservableError;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(((MainApp)application).getExecutors());
        mObservableListProducts = mDataRepository.getProducts();
        mObservableError = mDataRepository.getObservableError();
        mDataRepository.fetchProducts(keyword, pageNum);
    }

    public LiveData<List<PhotoModel>> getObservableListProducts() {
        return mObservableListProducts;
    }

    public LiveData<String> getObservableError() {
        return mObservableError;
    }

    public void fetchMore() {
        pageNum++;
        mDataRepository.fetchProducts(keyword, pageNum);
    }
}
