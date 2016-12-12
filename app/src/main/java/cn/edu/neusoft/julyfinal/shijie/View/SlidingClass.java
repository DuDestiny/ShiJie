package cn.edu.neusoft.julyfinal.shijie.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by S on 2016/12/7.
 */

public class SlidingClass extends HorizontalScrollView {
    private LinearLayout linearLayout;
    private ViewGroup mMenu;
    private ViewGroup mcontent;
    private  int mScreenWidth;
    private int mMenuRightPadding=50;
    private boolean once;
    private int mMenuWidth;
    //未使用自定义属性时调用
    //自定义ViewGroup
    //1.onMeasure：决定内部View（子View）的宽和高
    //2.onLayout：决定子View的放置的位置
    //3.onTouchEvent：判断移动状态
    public SlidingClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕宽度
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth=displayMetrics.widthPixels;

        //把DP转换为PX
        mMenuRightPadding= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics());
    }
    // 设置子View和自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once==false){
            linearLayout= (LinearLayout) getChildAt(0);
            mMenu= (ViewGroup) linearLayout.getChildAt(0);
            mcontent= (ViewGroup) linearLayout.getChildAt(1);
            mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-mMenuRightPadding;
            mcontent.getLayoutParams().width=mScreenWidth;
            once=true;
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    //通过设置偏移量，讲menu隐藏
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            //x为正值时，滚动条向右移动，显示内容向左移动
            this.scrollTo(mMenuWidth,0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int ScrollX=getScrollX();
                if(ScrollX>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                }else {
                    this.smoothScrollTo(0,0);
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }
}
