package mapara.flickrtest.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mapara on 2/26/17.
 */

public class PhotoModel {
    /** sample response
     { "id": "30142020893", "owner": "62544885@N04", "secret": "8f00297228", "server": "5739",
     "farm": 6, "title": "細蓉. #canton #cantonesefood #food", "ispublic": 1, "isfriend": 0, "isfamily": 0 }
     */
    private static final String mUrl = "http://farm%s.static.flickr.com/%s/%s_%s_%s.jpg";
    private static final String thumbnail_prefix = "t";
    private static final String original_prefix = "b";

    @SerializedName("id")
    private String mId;

    @SerializedName("farm")
    private String mFarm;

    @SerializedName("server")
    private String mServer;

    @SerializedName("secret")
    private String mSecret;

    @SerializedName("title")
    private String mTitle;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFarm() {
        return mFarm;
    }

    public void setFarm(String farm) {
        mFarm = farm;
    }

    public String getServer() {
        return mServer;
    }

    public void setServer(String server) {
        mServer = server;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return getThumbnailImgUrl();
    }

    public String getThumbnailImgUrl() {
        return String.format(mUrl, mFarm, mServer, mId, mSecret, thumbnail_prefix);
    }

    public String getOriginalImgUrl() {
        return String.format(mUrl, mFarm, mServer, mId, mSecret, original_prefix);
    }
}
