package liufantech.com.yingxiongmao.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by HL0521 on 2015/10/12.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter {

    private String mCategory;

    private int mPosition;

    public MainRecyclerViewAdapter(String category) {
        this.mCategory = category;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(TextView itemView) {
            super(itemView);

            mTextView = itemView;
        }

        public TextView getmTextView() {
            return mTextView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        mPosition = position;
        viewHolder.getmTextView().setText(mCategory + mPosition);
    }

    @Override
    public int getItemCount() {
        return 200;
    }

    public int getmPosition() {
        return mPosition;
    }
}
