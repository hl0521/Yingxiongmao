package liufantech.com.yingxiongmao.content;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by HL0521 on 2015/10/13.
 */
public abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
    private int mScrollThreshold = 10;

    public abstract void onScrolledUp();

    public abstract void onScrolledDown();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;

        if (isSignificantDelta) {
            if (dy > 0) {
                onScrolledUp();
            } else {
                onScrolledDown();
            }
        }
    }

    public void setmScrollThreshold(int mScrollThreshold) {
        this.mScrollThreshold = mScrollThreshold;
    }
}
