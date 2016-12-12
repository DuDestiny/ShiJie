package cn.edu.neusoft.julyfinal.shijie.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.edu.neusoft.julyfinal.shijie.NewsApp;
import cn.edu.neusoft.julyfinal.shijie.db.NewsListDB;
import cn.edu.neusoft.julyfinal.shijie.entity.News;
import cn.edu.neusoft.julyfinal.shijie.entity.NewsDetail;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class JsonUtil {
    public static List<News> parseJSONToList(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("stories");
        List<News> newsList = new ArrayList<News>();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject newsInJson = jsonArray.getJSONObject(i);
            int id = newsInJson.optInt("id");
            String title = newsInJson.optString("title");
            String image = "";
            if (newsInJson.has("images")){
                image =(String) newsInJson.getJSONArray("images").get(0);
            }
            News news = new News(id,title,image);
            //将数据添加到数据库
            if(!NewsListDB.getInstance(NewsApp.getContext()).isExist(news)){
                NewsListDB.getInstance(NewsApp.getContext()).saveNewsList(news);
            }
            newsList.add(news);
        }
        return newsList;
    }
    public static NewsDetail parseJsonToDetail(String json) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(json, NewsDetail.class);
    }
}