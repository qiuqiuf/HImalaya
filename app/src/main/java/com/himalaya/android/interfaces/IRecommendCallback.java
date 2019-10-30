package com.himalaya.android.interfaces;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendCallback {
    /**
     * 获取推荐内容的结果
     * @param result
     */
    void onRecommendListLoaded(List<Album> result);
    /**
     * 加载更多
     * @param result
     */
    void onLaoderMore(List<Album> result);
    /**
     * 下拉加载更多的结果
     * @param result
     */
    void onRefreshMore(List<Album> result);
}
