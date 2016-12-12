package cn.edu.neusoft.julyfinal.shijie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import cn.edu.neusoft.julyfinal.shijie.R;
import cn.edu.neusoft.julyfinal.shijie.db.NewsContentDB;
import cn.edu.neusoft.julyfinal.shijie.db.NewsDB;
import cn.edu.neusoft.julyfinal.shijie.entity.News;
import cn.edu.neusoft.julyfinal.shijie.entity.NewsDetail;
import cn.edu.neusoft.julyfinal.shijie.task.NewsContentTask;
import cn.edu.neusoft.julyfinal.shijie.util.CheckNet;
import cn.edu.neusoft.julyfinal.shijie.util.WebViewUtil;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class NewsContent extends AppCompatActivity {
    private News news;
    private WebView webView;
    private boolean isConnected = true;

    private boolean isFavorite = false;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
        news = (News) getIntent().getSerializableExtra("news");
        isFavorite = NewsDB.getInstance(this).isFavorite(news);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        isConnected = CheckNet.checkNetworkConnection(this);
        if (NewsContentDB.getInstance(this).isExist(news)){
            NewsDetail newsDetail = NewsContentDB.getInstance(this).getNewsDetail(news);
            WebViewUtil.showWebView(webView,newsDetail);
        }else{
            if (isConnected){
                new NewsContentTask(webView).execute(news.getId());
            }else{
                CheckNet.noNetworkAlert(this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_favourite,menu);
        if (isFavorite){
            menu.findItem(R.id.favorite_button).setIcon(R.drawable.fav_active);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (isFavorite){
            NewsDB.getInstance(this).deleteNews(news);
            item.setIcon(R.drawable.fav_normal);
            isFavorite = false;
        }else{
            NewsDB.getInstance(this).saveNews(news);
            item.setIcon(R.drawable.fav_active);
            isFavorite = true;
        }
        return true;
    }

    public static void actionStart(Context context, News news){
        Intent intent = new Intent(context,NewsContent.class);
        intent.putExtra("news",news);
        context.startActivity(intent);
    }
}