package com.himalaya.android.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.himalaya.android.R;
import com.himalaya.android.base.BaseApplication;

public abstract class UILoader extends FrameLayout {
    private View mLoadingView;
    private View  mSuceessView;
    private View mNetworkErrorView;
    private View mEmptyView;
    private onRetryListener mOnRetryListener=null;

    public enum UIStatus{
    LOADING,SUCCESS,NETWORK_ERROR,EMPTY,NONE
}
public UIStatus mCurrentStatus=UIStatus.NONE;
    public UILoader(@NonNull Context context) {
        this(context,null);

    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);

    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        init();
    }
    public  void updateStatus(UIStatus status) {
        mCurrentStatus=status;
        //更新UI一定要在主线程上
        BaseApplication.getsHandler().post(new Runnable() {
            @Override
            public void run() {
                switchUIByCurrentStatus();
            }
        });
    }


/*
*初始化UI
 */
    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        //加载中
        if(mLoadingView==null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        //根据状态设置是否可见
        mLoadingView.setVisibility(mCurrentStatus==UIStatus.LOADING?VISIBLE:GONE);
        //成功
        if(mSuceessView==null) {
            mSuceessView = getSuccessView(this);
            addView(mSuceessView);
        }
        //根据状态设置是否可见
        mSuceessView.setVisibility(mCurrentStatus==UIStatus.SUCCESS?VISIBLE:GONE);
        //网络错误页面
        if(mNetworkErrorView==null) {
            mNetworkErrorView = getNetworkErrorView();
            addView(mNetworkErrorView);
        }
        //根据状态设置是否可见
        mNetworkErrorView.setVisibility(mCurrentStatus==UIStatus.NETWORK_ERROR?VISIBLE:GONE);
       //数据为空界面
        if(mEmptyView==null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        //根据状态设置是否可见
        mEmptyView.setVisibility(mCurrentStatus==UIStatus.EMPTY?VISIBLE:GONE);

    }

    private View getEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view,this,false);
    }

    private View getNetworkErrorView() {
       View netWorkErrorView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view,this,false);
        netWorkErrorView.findViewById(R.id.network_error_icon);
        netWorkErrorView.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                //重新加载数据
              if(mOnRetryListener!=null){
                  mOnRetryListener.OnRetryClick();
              }
            }
        });
       return netWorkErrorView;
    }


    protected abstract View getSuccessView(ViewGroup container);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view,this,false);
    }
    public void setOnRetryListener(onRetryListener listener){
        mOnRetryListener=listener;
    }
    public interface onRetryListener{
        void OnRetryClick();
    }
    }

