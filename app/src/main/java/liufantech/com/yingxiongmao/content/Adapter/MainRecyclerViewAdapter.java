package liufantech.com.yingxiongmao.content.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.cache.AppCache;
import liufantech.com.yingxiongmao.custom.manager.OkHttpClientManager;

/**
 * Created by HL0521 on 2015/10/12.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    private String mCategory;

    private int mPosition;

    private AppCache mAppCache;

    /**
     * this parameter store the URL of picture shown in a cardview
     */
    private List<String> mPicUrlList;

    private OkHttpClientManager mOkHttpClientManager;

    public MainRecyclerViewAdapter(String category, List<String> picUrlList, AppCache appCache) {
        this.mCategory = category;
        mPicUrlList = picUrlList;
        mOkHttpClientManager = OkHttpClientManager.getInstance();
        mAppCache = appCache;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mShareFunction;
        TextView mCommentFunction;
        TextView mLikeFunction;

        public MyViewHolder(View view) {
            super(view);

            mImageView = (ImageView) view.findViewById(R.id.picCardView);
            mShareFunction = (TextView) view.findViewById(R.id.shareCardView);
            mCommentFunction = (TextView) view.findViewById(R.id.commentCardView);
            mLikeFunction = (TextView) view.findViewById(R.id.likeCardView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        mPosition = position;
        viewHolder.mLikeFunction.setText("" + mPosition);
        viewHolder.mCommentFunction.setText("" + mPosition);

        Bitmap bitmap = mAppCache.getAsBitmap(mPicUrlList.get(mPosition));
        if (bitmap == null) {
            try {
                OkHttpClientManager.downloadImageAsyn(viewHolder.mImageView, mPicUrlList.get(mPosition),
                        R.drawable.icon_pink_like, mAppCache);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            viewHolder.mImageView.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return mPicUrlList.size();
    }

    public int getmPosition() {
        return mPosition;
    }
}
