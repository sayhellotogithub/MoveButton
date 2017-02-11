package com.iblogstreet.movebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity
        extends AppCompatActivity
        implements View.OnTouchListener, View.OnClickListener
{
    RelativeLayout mRlayout;
    Button mBtnOverHand;
    TextView mTvMod;
    private boolean mIsClick;
    private int     mLastX;
    private int     mLastY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRlayout= (RelativeLayout) findViewById(R.id.rlLayout);
        mBtnOverHand= (Button) findViewById(R.id.btnOverHand);
        mTvMod=(TextView)findViewById(R.id.tvMod);
        mBtnOverHand.setOnTouchListener(this);
        mBtnOverHand.setOnClickListener(this);
        mTvMod.setOnClickListener(this);

    }
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        switch(event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                mIsClick = false;
//                mLastX=(int)event.getRawX();//获取触摸事件触摸位置的原始X坐标
//                mLastY=(int)event.getRawY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                mIsClick=true;
//
//                int dx=(int)event.getRawX()-mLastX;
//                int dy=(int)event.getRawY()-mLastY;
//
//                int l=v.getLeft()+dx;
//                int b=v.getBottom()+dy;
//                int r=v.getRight()+dx;
//                int t=v.getTop()+dy;
//
//                if(l<0){
//                    l=0;
//                    r=l+v.getWidth();
//                }
//
//                if(t<0){
//                    t=0;
//                    b=t+v.getHeight();
//                }
//
//                if(r>mRlayout.getWidth()){
//                    r=mRlayout.getWidth();
//                    l=r-v.getWidth();
//                }
//
//                if(b>mRlayout.getHeight()){
//                    b=mRlayout.getHeight();
//                    t=b-v.getHeight();
//                }
//                v.layout(l, t, r, b);
//
//                mLastX=(int)event.getRawX();
//                mLastY=(int)event.getRawY();
//
//                v.postInvalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return mIsClick;
//    }

    //拖动事件处理
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v.getId() != R.id.btnOverHand)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //v.setBackgroundResource(R.mipmap.btn_style_one_press);
                mIsClick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();//按钮初始的横纵坐标

                break;
            case MotionEvent.ACTION_MOVE:
                mIsClick = true;//当按钮被移动的时候设置isclick为true
                float currentX = event.getRawX();
                float currentY = event.getRawY();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                int dx = (int) (currentX - mLastX);
                int dy = (int) (currentY - mLastY);//按钮被移动的距离
                int l =layoutParams.leftMargin + dx;
                int t = layoutParams.topMargin + dy;
                int b =mRlayout.getHeight()- t-v.getHeight();
                int r =mRlayout.getWidth()- l-v.getWidth();
                if (l < 0) {//处理按钮被移动到上下左右四个边缘时的情况，决定着按钮不会被移动到屏幕外边去
                    l = 0;
                    r =mRlayout.getWidth()-v.getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = mRlayout.getHeight()-v.getHeight();
                }

                if (r<0) {
                    r =0;
                    l = mRlayout.getWidth()-v.getWidth();
                }
                if (b<0) {
                    b = 0;
                    t = mRlayout.getHeight()-v.getHeight();
                }
                layoutParams.leftMargin = l;
                layoutParams.topMargin = t;
                layoutParams.bottomMargin = b;
                layoutParams.rightMargin = r;

                v.setLayoutParams(layoutParams);
                mLastX = (int) currentX;
                mLastY = (int) currentY;
                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return mIsClick;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOverHand:
                Toast.makeText(this,"举手",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvMod:
                mTvMod.setText("success");
                break;
        }
    }
}
