package cn.edu.neusoft.julyfinal.shijie.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cn.edu.neusoft.julyfinal.shijie.NewsApp;
import cn.edu.neusoft.julyfinal.shijie.db.NewsContentDB;
import cn.edu.neusoft.julyfinal.shijie.entity.News;
import cn.edu.neusoft.julyfinal.shijie.entity.NewsDetail;
import cn.edu.neusoft.julyfinal.shijie.util.HttpUtil;
import cn.edu.neusoft.julyfinal.shijie.util.JsonUtil;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class DownloadNewsTask extends AsyncTask<List<News>,Void,Void> {

    private NewsDetail newsDetail;
    @Override
    protected Void doInBackground(List<News>...params){
        for (int i =0;i<params[0].size();i++){
            try{
                newsDetail = JsonUtil.parseJsonToDetail(HttpUtil.getNewsDetail(params[0].get(i).getId())) ;
                if (!NewsContentDB.getInstance(NewsApp.getContext()).isExist(newsDetail)){
                    NewsContentDB.getInstance(NewsApp.getContext()).saveNewsContent(newsDetail,params[0].get(i).getId());
                }
            }catch (IOException |JSONException e){
                e.printStackTrace();
            }

        }return null;
    }

}