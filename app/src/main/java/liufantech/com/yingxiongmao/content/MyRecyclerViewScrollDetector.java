package liufantech.com.yingxiongmao.content;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

/**
 * Created by HL0521 on 2015/10/13.
 */
public class MyRecyclerViewScrollDetector extends RecyclerViewScrollDetector {

    private FloatingActionButton mFloatingActionButton;
    private String mCategory;

    public MyRecyclerViewScrollDetector() {

    }

    public MyRecyclerViewScrollDetector(String category, FloatingActionButton floatingActionButton) {
        mFloatingActionButton = floatingActionButton;
        mCategory = category;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        /**
         * newState: 0->已停止  1->滑动中（手指还在屏幕上）  2->滑动中（手指已离开屏幕，靠惯性滑动）
         *
         * 目的：（快速）向下滑动过程中，屏幕停止前，还未加载的资源（指从SDcard或网络加载），暂不加载
         * 待屏幕停止滑动后，加在当前需要显示在屏幕中的资源
         */
        if (LoadResourceManager.getInstance() != null) {
            switch (newState) {
                case 0:
                    LoadResourceManager.getInstance().resume(mCategory);
                    break;
                case 1:
                    LoadResourceManager.getInstance().resume(mCategory);
                    break;
                case 2:
                    LoadResourceManager.getInstance().suspend();
                    break;
            }
        }
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
