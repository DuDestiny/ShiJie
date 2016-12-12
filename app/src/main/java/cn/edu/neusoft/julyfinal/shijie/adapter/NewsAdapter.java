package cn.edu.neusoft.julyfinal.shijie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.edu.neusoft.julyfinal.shijie.R;
import cn.edu.neusoft.julyfinal.shijie.entity.News;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private LayoutInflater inflater;
    private int resourceId;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.no_image)
            .showImageOnFail(R.drawable.no_image)
            .showImageForEmptyUri(R.drawable.no_image)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    public NewsAdapter(Context context, int textViewResourceId){
        super(context,textViewResourceId);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = textViewResourceId;
    }
    public NewsAdapter(Context context,int textViewResourceId,List<News> newsList){
        super(context,textViewResourceId,newsList);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder viewHolder;
        View view;
        if (convertView == null){
            view = inflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.news_image);
            viewHolder.textView = (TextView)view.findViewById(R.id.news_title);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        News news = getItem(position);
        viewHolder.textView.setText(news.getTitle());
        imageLoader.displayImage(news.getImage(),viewHolder.imageView,options);
        return view;
    }
    public void refreshNewsList(List<News> newsList) {
        clear();
        addAll(newsList);
        notifyDataSetChanged();
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}