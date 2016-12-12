package cn.edu.neusoft.julyfinal.shijie;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by JulyFinal on 2016/11/19.
 */

public class NewsApp extends Application {
    private static Context context;
    //初始化
    public static void initImageLoader(Context context) {
        //一些相关的设置（均为默认）
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)

                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        //初始化设置
        ImageLoader.getInstance().init(config);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initImageLoader(this);
    }
    public static Context getContext(){
        return context;
    }
}

