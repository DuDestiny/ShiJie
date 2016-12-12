package cn.edu.neusoft.julyfinal.shijie.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import cn.edu.neusoft.julyfinal.shijie.adapter.NewsAdapter;
import cn.edu.neusoft.julyfinal.shijie.entity.News;
import cn.edu.neusoft.julyfinal.shijie.util.HttpUtil;
import cn.edu.neusoft.julyfinal.shijie.util.JsonUtil;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class NewsAsyncTask extends AsyncTask<Void,Void,List<News>> {
    private NewsAdapter adapter;
    private onFinishListener listener;
    public NewsAsyncTask(NewsAdapter adapter){
        super();
        this.adapter = adapter;
    }
    public NewsAsyncTask(NewsAdapter adapter,onFinishListener listener){
        super();
        this.adapter = adapter;
        this.listener = listener;
    }

    @Override
    protected List<News> doInBackground(Void... voids) {
        List<News> newsList = null;
        try{
            newsList = JsonUtil.parseJSONToList(HttpUtil.getNewsList());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }finally {
            return newsList;
        }
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter.refreshNewsList(newsList);
        if (listener != null){
            listener.afterTaskFinish();
        }
    }
    public interface onFinishListener{
        public void afterTaskFinish();

    }
}