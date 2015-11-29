package liufantech.com.yingxiongmao.content.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by HL0521 on 2015/11/25.
 */
public class PicRamCache {

    private int cacheSize = 25 * 1024 * 1024;  // 25MBit

    private static PicRamCache mPicRamCache;
    private LruCache<String, Bitmap> mLruCache;


    public static PicRamCache getInstance() {
        if (mPicRamCache == null) {
            synchronized (PicRamCache.class) {
                if (mPicRamCache == null) {
                    mPicRamCache = new PicRamCache();
                }
            }
        }

        return mPicRamCache;
    }

    private PicRamCache() {
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
            }

            /**
             *
             * @param key 需要查找的图片的 url，先根据 url 的值从 SDcard 中寻找相应的 Bitmap；如果没有
             *            找到，则需要从网络下载（由 SDcardCahce 处理）
             * @return 总是返回 null，以确保程序程序顺序往下执行，不能让其阻塞线程。当从 SDcardCache 或
             * 从网络下载到 Bitmap 后，再采用其它方式将其加入到 SDcardCache 和 PicRamCache 中，并更新
             * 相关 ImageView 中的图片。
             */
            @Override
            protected Bitmap create(String key) {
//                return super.create(key);
                return null;
            }
        };
    }

    public void setCacheSize(int cacheSize) {
        mLruCache.resize(cacheSize);
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public LruCache<String, Bitmap> getmLruCache() {
        return mLruCache;
    }
}
