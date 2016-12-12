package cn.edu.neusoft.julyfinal.shijie.activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by S on 2016/12/12.
 */

public class MyOrientationListener implements SensorEventListener {
   private SensorManager manager;
    private Sensor sensor;
    private Context context;

    //记录X轴
    private float lastX;

    public MyOrientationListener(Context context){
        this.context=context;
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType()==Sensor.TYPE_ORIENTATION){
            float x=sensorEvent.values[SensorManager.DATA_X];
            if(Math.abs(x-lastX)>1.0){
                if (onOrientationListener!=null){
                    onOrientationListener.onOrientationChanged(x);
                }
            }
            lastX=x;
        }
    }
    //开始监听
    @SuppressWarnings("deprecation")
public void start(){
    //获取传感服务
manager= (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
if(manager!=null){
    //获得方向传感器
   sensor= manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
}
    if(sensor!=null){
        manager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }
}
    public void stop(){
        manager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private OnOrientationListener onOrientationListener;

    public OnOrientationListener getOnOrientationListener() {
        return onOrientationListener;
    }

    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.onOrientationListener = onOrientationListener;
    }

    public interface OnOrientationListener{
        void onOrientationChanged(float x);
    }
}
