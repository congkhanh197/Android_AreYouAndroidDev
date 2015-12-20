package com.hasbrain.areyouandroiddev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hasbrain.areyouandroiddev.R;
import com.hasbrain.areyouandroiddev.model.RedditPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khanh Ultron on 12/17/2015.
 */
public class ListViewAdapter extends ArrayAdapter<RedditPost> {
    public static final int TYPE_FOOTER_ITEM = 0;
    public static final int TYPE_NORMAL_ITEM = 1;
    private Context context;
    private List<RedditPost> redditPosts;

    public ListViewAdapter(Context context, List<RedditPost> redditPosts) {
        super(context, 0, redditPosts);
        this.context = context;
        this.redditPosts = redditPosts;
    }

    @Override
    public int getCount() {
        return redditPosts.size() + 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1)
            return TYPE_NORMAL_ITEM;
        else return TYPE_FOOTER_ITEM;

        // return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (type == TYPE_NORMAL_ITEM) {
                v = inflater.inflate(R.layout.text_view, null);
            } else {
                v = inflater.inflate(R.layout.type_view, null);
            }

//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
        if (type != TYPE_NORMAL_ITEM) {
            RedditPost redditPost = redditPosts.get(position);

            if (redditPost != null) {
                TextView tv_score = (TextView) v.findViewById(R.id.tv_score);
                tv_score.setText(redditPost.getScore() + "");
                TextView tv_au = (TextView) v.findViewById(R.id.tv_author);
                tv_au.setText(redditPost.getAuthor());
                tv_au.setTextColor(0xff0a295a);
                TextView tv_sub = (TextView) v.findViewById(R.id.tv_subreddit);
                tv_sub.setText(redditPost.getSubreddit());
                tv_sub.setTextColor(0xff0a295a);

                TextView tv_tittle = (TextView) v.findViewById(R.id.tv_title);
                tv_tittle.setText(redditPost.getTitle());
                if (redditPost.isStickyPost())
                    tv_tittle.setTextColor(0xff387801);
                else tv_tittle.setTextColor(0xff000000);
                TextView tv_cmt_dm_cd = (TextView) v.findViewById(R.id.tv_comment_domain_createdutc);
                tv_cmt_dm_cd.setText(redditPost.getCommentCount() + " • " + redditPost.getDomain() + " • " + redditPost.getCreatedUTC());
            }
        }

        //ViewHolder pattern? why do we need to use viewholder?
        //LayoutInflater: meaning of  `parent` argument
        //

        return v;
    }
}
