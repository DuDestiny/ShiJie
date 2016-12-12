package cn.edu.neusoft.julyfinal.shijie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.edu.neusoft.julyfinal.shijie.R;
import cn.edu.neusoft.julyfinal.shijie.adapter.NewsAdapter;
import cn.edu.neusoft.julyfinal.shijie.db.NewsDB;

public class FavoriteActivity extends AppCompatActivity {
    private ListView favListView;
    private NewsAdapter favNewsAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_layout);
        favNewsAdapter = new NewsAdapter(this,R.layout.list_view_item, NewsDB.getInstance(this).loadFavorite());
        favListView = (ListView)findViewById(R.id.list_view_favourite);
        favListView.setAdapter(favNewsAdapter);
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsContent.actionStart(FavoriteActivity.this,favNewsAdapter.getItem(position));
            }
        });
    }

    /**
     * 返回收藏夹界面时，刷新
     */
    @Override
    protected void onResume(){
        super.onResume();
        // Log.d("fav","onResume");
        favNewsAdapter = new NewsAdapter(this,R.layout.list_view_item,NewsDB.getInstance(this).loadFavorite());
        favListView.setAdapter(favNewsAdapter);
    }
    public static void startFavoriteActivity(Context context){
        Intent intent = new Intent(context,FavoriteActivity.class);
        context.startActivity(intent);
    }
}