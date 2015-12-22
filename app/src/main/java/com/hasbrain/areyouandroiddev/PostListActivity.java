package com.hasbrain.areyouandroiddev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hasbrain.areyouandroiddev.adapter.ListViewAdapter;
import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;
import com.hasbrain.areyouandroiddev.datastore.FileBasedFeedDataStore;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    ArrayList<RedditPost> redditPosts;
    ListViewAdapter lvAdapter;
    ListView listView;
    GridView gridView;
    private boolean isLandscape;

    public static final String DATA_JSON_FILE_NAME = "data.json";
    private FeedDataStore feedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        listView = (ListView) findViewById(R.id.list);
        gridView = (GridView) findViewById(R.id.grid);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
        Gson gson = gsonBuilder.create();
        InputStream is = null;
        try {
            is = getAssets().open(DATA_JSON_FILE_NAME);
            feedDataStore = new FileBasedFeedDataStore(gson, is);
            feedDataStore.getPostList(new FeedDataStore.OnRedditPostsRetrievedListener() {
                @Override
                public void onRedditPostsRetrieved(List<RedditPost> postList, Exception ex) {
                    displayPostList(postList);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    protected void displayPostList(final List<RedditPost> postList) {
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        lvAdapter = new ListViewAdapter(this, postList, isLandscape);

        if (!isLandscape) {
            listView.setAdapter(lvAdapter);
//            setClick(listView, postList);
            listView.setOnItemClickListener(new OnPostClickListener(postList, this));
        }else {

            gridView.setAdapter(lvAdapter);
            setClick(gridView,postList);


        }

    }
    protected void setClick (AbsListView view,final List<RedditPost> postList){
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), PostViewActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (position != postList.size()) {
                    myIntent.putExtra("url", postList.get(position).getUrl());
                } else {
                    myIntent.putExtra("url", getString(R.string.url));
                }
                startActivity(myIntent);
            }
        });

    }
    //TODO: static class vs non-static class??? final???
    private static class OnPostClickListener implements AdapterView.OnItemClickListener {
        private List<RedditPost> postList;
        private Context context;

        public OnPostClickListener(List<RedditPost> postList, Context context) {
            this.postList = postList;
            this.context = context;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent myIntent = new Intent(view.getContext(), PostViewActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (position != postList.size()) {
                myIntent.putExtra("url", postList.get(position).getUrl());
            } else {
                myIntent.putExtra("url", context.getString(R.string.url));
            }
            context.startActivity(myIntent);
        }
    }

    protected int getLayoutResource() {
        return R.layout.activity_post_list;
    }


}
