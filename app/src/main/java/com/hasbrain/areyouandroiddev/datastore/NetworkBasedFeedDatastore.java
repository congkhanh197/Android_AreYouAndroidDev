package com.hasbrain.areyouandroiddev.datastore;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/22/15.
 */
public class NetworkBasedFeedDatastore implements FeedDataStore {
    private String baseUrl;

    @Override
    public void getPostList(String topic, String before, String after,
            OnRedditPostsRetrievedListener onRedditPostsRetrievedListener) {
        //TODO: Implement network calls.
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
