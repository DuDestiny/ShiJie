package cn.edu.neusoft.julyfinal.shijie.task;

import android.os.AsyncTask;
import android.webkit.WebView;

import org.json.JSONException;

import java.io.IOException;

import cn.edu.neusoft.julyfinal.shijie.entity.NewsDetail;
import cn.edu.neusoft.julyfinal.shijie.util.HttpUtil;
import cn.edu.neusoft.julyfinal.shijie.util.JsonUtil;
import cn.edu.neusoft.julyfinal.shijie.util.WebViewUtil;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class NewsContentTask extends AsyncTask<Integer,Void,NewsDetail> {
    private NewsDetail newsDetail;
    private WebView webView;

    public NewsContentTask(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        try {
            newsDetail = JsonUtil.parseJsonToDetail(HttpUtil.getNewsDetail(params[0]));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            return newsDetail;
        }
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        WebViewUtil.showWebView(webView, newsDetail);
    }
}