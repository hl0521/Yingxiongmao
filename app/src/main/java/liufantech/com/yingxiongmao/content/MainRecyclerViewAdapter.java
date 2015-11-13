package liufantech.com.yingxiongmao.content;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import liufantech.com.yingxiongmao.R;

/**
 * Created by HL0521 on 2015/10/12.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    private String mCategory;

    private int mPosition;

    public MainRecyclerViewAdapter(String category) {
        this.mCategory = category;
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
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public int getmPosition() {
        return mPosition;
    }
}
