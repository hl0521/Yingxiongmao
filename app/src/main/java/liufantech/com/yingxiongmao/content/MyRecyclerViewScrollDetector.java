package liufantech.com.yingxiongmao.content;

import android.support.design.widget.FloatingActionButton;

/**
 * Created by HL0521 on 2015/10/13.
 */
public class MyRecyclerViewScrollDetector extends RecyclerViewScrollDetector {

    private FloatingActionButton mFloatingActionButton;

    public MyRecyclerViewScrollDetector() {

    }

    public MyRecyclerViewScrollDetector(FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
    }

    @Override
    public void onScrolledUp() {
        mFloatingActionButton.hide();
    }

    @Override
    public void onScrolledDown() {
        mFloatingActionButton.show();
    }
}
