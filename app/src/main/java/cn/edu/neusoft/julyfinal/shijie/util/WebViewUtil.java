package cn.edu.neusoft.julyfinal.shijie.util;

import android.webkit.WebView;

import cn.edu.neusoft.julyfinal.shijie.entity.NewsDetail;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class WebViewUtil {
    public static void showWebView(WebView webView, NewsDetail newsDetail){
        String headerImage;
        headerImage = newsDetail.getImage();
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(headerImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        webView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }
}
