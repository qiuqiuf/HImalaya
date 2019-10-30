package com.himalaya.android.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.himalaya.android.R;
import com.himalaya.android.adapters.RecommendListAdapter;
import com.himalaya.android.base.BaseFragment;
import com.himalaya.android.interfaces.IRecommendCallback;
import com.himalaya.android.presenters.RecommendPresenter;
import com.himalaya.android.utils.Constants;
import com.himalaya.android.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.GussLikeAlbumList;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment extends BaseFragment implements IRecommendCallback {
    private String TAG="RecommendFragment";
    View  mRootView;
    RecyclerView mRecommendRv;
    RecommendListAdapter mRecommendListAdapter;
    RecommendPresenter mRecommendPresenter;
    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
       //View 加载完成

        mRootView = layoutInflater.inflate(R.layout.fragment_recommend,container,false);


        mRecommendRv = mRootView.findViewById(R.id.recommend_list);
        //RecyclerView 的使用
        //找到控件
        //设置布局管理器
        LinearLayoutManager LinearLayoutManager=new LinearLayoutManager(getContext());
        LinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecommendRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top= UIUtil.dip2px(view.getContext(),5);
                outRect.bottom= UIUtil.dip2px(view.getContext(),5);
                outRect.left=UIUtil.dip2px(view.getContext(),5);
                outRect.right=UIUtil.dip2px(view.getContext(),5);
            }
        });
                //设置适配器

                mRecommendListAdapter = new RecommendListAdapter();
        mRecommendRv.setAdapter(mRecommendListAdapter);
        mRecommendRv.setLayoutManager(LinearLayoutManager);
        //获取逻辑层的对象

        mRecommendPresenter = RecommendPresenter.getInstance();
        //先要设置的通知接口注册
        mRecommendPresenter.registerViewCallback(this);
        //获取推荐列表
        mRecommendPresenter.getRecommendList();

        //返回View，界面显示
        return mRootView;
    }

    /*获取推荐内容，其实就是喜欢
    *这个接口：3.10.6 获取猜你喜欢的专辑
    * */
   /* private void getRecommendData() {
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
                 upRecommendui(albumList);
             }
            }

            @Override
            public void onError(int i, String s) {
                LogUtil.d(TAG,"error code -- >"+i);
                LogUtil.d(TAG,"error massage -- > "+s);
            }
        })
    }
    */



    @Override
    public void onRecommendListLoaded(List<Album> result) {
        //当我们获取到推荐内容的时候，这个方法就会被调用 成功了
        //数据回来以后，就是更新UI了
        mRecommendListAdapter .setData(result);
    }

    @Override
    public void onLaoderMore(List<Album> result) {

    }

    @Override
    public void onRefreshMore(List<Album> result) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消接口的注册
        if (mRecommendPresenter != null) {
            mRecommendPresenter.unRegisterViewCallback(this);
        }
    }
}
