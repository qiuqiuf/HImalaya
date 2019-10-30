package com.himalaya.android.interfaces;

public interface IRecommendPresenter {
    /**
     * 获取1推荐内容
     */
    void getRecommendList();

/*
下拉刷新更多内容
 */
void pull2RefresMore();

/*
*上接加载更多
 */
void loadMore();
/*

这个方法用于注册UI的回调
 */
void registerViewCallback(IRecommendCallback callback);
    /*

    这个方法用于取消UI的回调
     */
void unRegisterViewCallback(IRecommendCallback callback);
}

