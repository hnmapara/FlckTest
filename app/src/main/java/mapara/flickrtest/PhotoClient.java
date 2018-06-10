package mapara.flickrtest;

import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mapara.flickrtest.Model.PhotoModel;
import mapara.flickrtest.Model.PhotosResponseModel;
import mapara.flickrtest.io.TestAppApiService;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mapara on 2/26/17.
 */

public class PhotoClient {
    private static final String TAG = "PhotoClient";
    private final static String API_KEY = "ca465189cc797c8dfa35b5581454406b";
    private final static String METHOD_SEARCH_PHOTO = "flickr.photos.search";
    private final static String METHOD_GET_RECENT = "flickr.photos.getRecent";

    private static PhotoClient mInstance;
    private TestAppApiService mRetrofitService;
    private List<PhotoModel> mList;
    private int mCurrentPage;

    public interface IOnFinish {
        void onSuccess(boolean isSuccess);
    }

    public List<PhotoModel> getPhotos() {
        return mList;
    }

    private PhotoClient() {
        mList = new ArrayList<>();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .addQueryParameter("format", "json")
                        .addQueryParameter("nojsoncallback", "1")
                        //.addQueryParameter("page", Integer.toString(page))
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mRetrofitService = restAdapter.create(TestAppApiService.class);
    }


    public static synchronized PhotoClient getInstance() {
        if (mInstance == null) {
            mInstance = new PhotoClient();
        }
        return mInstance;
    }

    // Unused
    @WorkerThread
    public List<PhotoModel> fetchPhotosForKeyWord(String key, int page) {
        if (mCurrentPage >= page) {
            return mList;
        }
        Call<PhotosResponseModel> call = mRetrofitService.getPhotos(METHOD_SEARCH_PHOTO, key, page);
        try {
            Response<PhotosResponseModel> response = call.execute();
            if (response.isSuccessful()) {
                mCurrentPage = page;
                mList.addAll(response.body().getPhotos());
                return mList;
            } else {
                Log.e(TAG, "fetchPhotosForKeyWords : "+ response.message());
            }
        } catch (IOException e) {
            Log.e(TAG, "fetchPhotosForKeyWords : ",e);
        }
        return mList;
    }

    // Unused
    public void fetchAsyncPhotosForKeyword(String key, @Nullable final IOnFinish listener) {
        mCurrentPage++;
        Log.d(TAG, "fetching page : " + mCurrentPage);
        Call<PhotosResponseModel> call = mRetrofitService.getPhotos(METHOD_SEARCH_PHOTO, key, mCurrentPage);
        call.enqueue(new Callback<PhotosResponseModel>() {
            @Override
            public void onResponse(Call<PhotosResponseModel> call, Response<PhotosResponseModel> response) {
                if (response.isSuccessful()) {
                    if (mList.size() != 0 && mList.get(mList.size() -1) == null) {
                        mList.remove(mList.size()-1);
                    }
                    mList.addAll(response.body().getPhotos());
                    Log.d(TAG, "onResponse :" + response.body());
                    if (listener != null) {
                        listener.onSuccess(true);
                    }
                } else {
                    Log.e(TAG, "onResponse " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PhotosResponseModel> call, Throwable t) {
                if (listener != null) {
                    listener.onSuccess(false);
                }
                Log.e(TAG, "onFailure", t);
            }
        });
    }

    // Unused
    private void enqueueRequestAndHandle(Call<PhotosResponseModel> call) {
        /*
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhotosResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPhotoManagerCallBack != null) {
                            mPhotoManagerCallBack.onSuccess(false);
                        }
                        Log.e(TAG, "onFailure", e);
                    }

                    @Override
                    public void onNext(PhotosResponseModel photosResponseModel) {
                        mList.addAll(photosResponseModel.getPhotos());
                        if (mPhotoManagerCallBack != null) {
                            mPhotoManagerCallBack.onSuccess(true);
                        }
                    }
                });
         */
    }

    @WorkerThread
    @Nullable
    public List<PhotoModel> fetchPhotos(String keyword, int page) {
        Call<PhotosResponseModel> call = mRetrofitService.getPhotos(METHOD_SEARCH_PHOTO, keyword, page);
        try {
            Response<PhotosResponseModel> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getPhotos();
            } else {
                Log.e(TAG, "fetchPhotos : "+ response.message());
            }
        } catch (IOException e) {
            Log.e(TAG, "fetchPhotos : ",e);
        }
        return null;
    }
}
