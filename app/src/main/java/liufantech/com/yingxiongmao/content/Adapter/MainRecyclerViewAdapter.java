package liufantech.com.yingxiongmao.content.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.LoadResourceManager;

/**
 * Created by HL0521 on 2015/10/12.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    private String mCategory;

    private int mPosition;

    private LoadResourceManager mLoadResourceManager;

    /**
     * this parameter store the URL of picture shown in a cardview
     */
    private List<String> mPicUrlList;

    public MainRecyclerViewAdapter(String category, List<String> picUrlList) {
        this.mCategory = category;
        mPicUrlList = picUrlList;

        mLoadResourceManager = LoadResourceManager.getInstance();
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

        Bitmap bitmap = mLoadResourceManager.loadBitmap(mPicUrlList.get(mPosition), viewHolder.mImageView);

        if (bitmap == null) {
            viewHolder.mImageView.setImageResource(R.drawable.icon_pink_like);
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
