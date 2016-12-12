package cn.edu.neusoft.julyfinal.shijie.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class HttpUtil {
    private static String NEWS = "http://news-at.zhihu.com/api/4/news/";
    private static String LATEST_NEWS = "http://news-at.zhihu.com/api/4/news/latest";

    public static String sendRequestWithHttpURLConnection(String urlAddress) throws IOException {
        HttpURLConnection connection = null;
        try{
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent","Mozilla/5.0");

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while((line=reader.readLine()) != null){
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
            else {
                throw new IOException("Network Error - response code:"+ connection.getResponseCode());
            }
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }

    }

    public static String getNewsList() throws IOException{
        return sendRequestWithHttpURLConnection(LATEST_NEWS);
    }
    public static String getNewsDetail(int id) throws IOException{
        return sendRequestWithHttpURLConnection(NEWS+id);
    }
}