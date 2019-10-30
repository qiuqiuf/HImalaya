package com.himalaya.android.presenters;

import com.himalaya.android.interfaces.IRecommendCallback;
import com.himalaya.android.interfaces.IRecommendPresenter;
import com.himalaya.android.utils.Constants;
import com.himalaya.android.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
private static final String TAG="RecommendPresenter";
private List<IRecommendCallback> mCallbacks =new ArrayList<>();
    private RecommendPresenter(){
    }
    private static RecommendPresenter sInstance=null;
    /*
    *获取单例对象
    *
    * @return
     */
    public static RecommendPresenter getInstance() {
        if (sInstance == null) {
            synchronized (RecommendPresenter.class) {
                if (sInstance == null) {
                    sInstance = new RecommendPresenter();
                }
            }
        }
        return sInstance;
    }




    @Override
    public void getRecommendList() {
//获取推荐内容
        //封装参数
        Map<String, String> map = new HashMap<>();
        //这个参数白哦是一页数据返回多少条
        map.put(DTransferConstants.LIKE_COUNT, Constants.RECOMMEND_COUNT+"");
        CommonRequest.getGuessLikeAlbum(map, new IDataCallBack<GussLikeAlbumList>() {
            @Override
            public void onSuccess(GussLikeAlbumList gussLikeAlbumList) {
                if(gussLikeAlbumList!=null){
                    List<Album> albumList = gussLikeAlbumList.getAlbumList();
                    //数据回来，更新Ui
                    // upRecommendui(albumList);
                    handlerRecommendResult(albumList);
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"error code -- >"+i);
                LogUtil.d(TAG,"error massage -- > "+s);
            }
        });

    }


    private void handlerRecommendResult(List<Album> albumList) {
       //通知UI更新
        if(mCallbacks!=null){
            for (IRecommendCallback callback : mCallbacks) {
                callback.onRecommendListLoaded(albumList);
            }
        }
    }

    @Override
    public void pull2RefresMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IRecommendCallback callback) {
if(mCallbacks!=null && !mCallbacks.contains(callback)){
    mCallbacks.add((callback));
        }
    }

    @Override
    public void unRegisterViewCallback(IRecommendCallback callback) {
        if(mCallbacks!=null){
            mCallbacks.remove((callback));
        }
    }
}
