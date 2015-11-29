package liufantech.com.yingxiongmao.content;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import liufantech.com.yingxiongmao.content.cache.PicRamCache;

/**
 * Created by HL0521 on 2015/11/27.
 */
public class LoadResourceManager {

    private final static String TAG = LoadResourceManager.class.getSimpleName();

    private Map<String, LoadStateChangedListener> mListenerHashMap = new HashMap<>();
    private LoadStateChangedListener mLoadStateChangedListener;

    public enum State {
        SUSPEND, RUNNING
    }

    private State mState = State.RUNNING;         // 默认是运行状态

    private static LoadResourceManager mLoadResourceManager;

    public static LoadResourceManager getInstance() {
        if (mLoadResourceManager == null) {
            synchronized (LoadResourceManager.class) {
                if (mLoadResourceManager == null) {
                    mLoadResourceManager = new LoadResourceManager();
                }
            }
        }

        return mLoadResourceManager;
    }

    private LoadResourceManager() {

    }

    public void addLoadStateChangedListener(String string, LoadStateChangedListener listener) {
        mListenerHashMap.put(string, listener);
    }

    /**
     * @param url
     * @return 如果 PicRamCache 中存在相应的 Bitmap，则直接返回 bitmap；如果需要从 SdcardCache 或者 从
     * 网络上下载，则返回 null。
     */
    public Bitmap loadBitmap(String url, ImageView imageView) {
        Bitmap bitmap;

        bitmap = PicRamCache.getInstance().getmLruCache().get(url);

        if (bitmap == null) {
            if (mState == State.RUNNING) {
                DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                downloadImageTask.execute(url);

                if (bitmap == null) {
                    return null;
                }
            }
        }

        return bitmap;
    }

    public void suspend() {
        if (mState == State.RUNNING) {
            mState = State.SUSPEND;
        }
    }

    public void resume(String category) {
        if (mState == State.SUSPEND) {
            mState = State.RUNNING;
            mLoadStateChangedListener = mListenerHashMap.get(category);
            if (mLoadStateChangedListener != null) {
                mLoadStateChangedListener.onResumed();
            }
        }
    }

    public interface LoadStateChangedListener {
        public void onResumed();
    }
}
