package mapara.flickrtest.io;

import mapara.flickrtest.Model.PhotosResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Api Service to parse json response
 */
public interface TestAppApiService {

    @GET("services/rest")
    Call<PhotosResponseModel> getPhotos(@Query("method") String method, @Query("text") String searchString, @Query("page") int page);

    @GET("services/rest")
    Call<PhotosResponseModel> getRecentPhotos(@Query("method") String method, @Query("page") int page);
}
