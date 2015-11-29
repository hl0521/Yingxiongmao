package liufantech.com.yingxiongmao.main;

import android.os.Environment;

import java.io.File;

/**
 * Created by HL0521 on 2015/10/12.
 */
public class MainConstant {

    // Fragment in ViewPager
    public static final String CATEGORY_HOMEPAGE = "liufantech.com.yingxiongmao.category.HOMEPAGE";
    public static final String CATEGORY_NBA = "liufantech.com.yingxiongmao.category.NBA";
    public static final String CATEGORY_FOOTBALL = "liufantech.com.yingxiongmao.category.FOOTBALL";
    public static final String CATEGORY_FITNESS = "liufantech.com.yingxiongmao.category.FUTNESS";
    public static final String CATEGORY_TENNIS = "liufantech.com.yingxiongmao.category.TENNIS";
    public static final String CATEGORY_BEAUTY = "liufantech.com.yingxiongmao.category.BEAUTY";

    // Login Fragment
    public static final String FRAGMENT_LOGIN = "liufantech.com.yingxiongmao.fragment.LOGIN";

    // LeanCloud key
    public static final String APP_ID = "pXmrJspKpp2yR9aorJ0m3aGu";
    public static final String APP_KEY = "HA0RQAycHfs4ByiYXBVghsJS";
    public static final String MASTER_KEY = "jKKfMOVSp899K2J5JythdGTG";


    // Following constants used for temporary
    public static final String PIC_URL0 = "http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg";
    public static final String PIC_URL1 = "http://pic.nipic.com/2007-11-09/200711912453162_2.jpg";
    public static final String PIC_URL2 = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
    public static final String PIC_URL3 = "http://pic.nipic.com/2007-11-09/200711912230489_2.jpg";
    public static final String PIC_URL4 = "http://www.th7.cn/d/file/p/2014/08/16/a49aa9b67582d1a28dbfe30e94b64444.gif";
    public static final String PIC_URL5 = "http://pic.nipic.com/2007-11-08/2007118192311804_2.jpg";
    public static final String PIC_URL6 = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
    public static final String PIC_URL7 = "http://pic14.nipic.com/20110522/7411759_164157418126_2.jpg";
    public static final String PIC_URL8 = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
    public static final String PIC_URL9 = "http://www.th7.cn/d/file/p/2014/08/16/a49aa9b67582d1a28dbfe30e94b64444.gif";


    // SDcard 缓存目录
    /**
     * PIC_CACHE   : cache for pictures
     * VIDEO_CACHE : cache for videos
     */
    public static final File PIC_CACHE = new File(Environment.getExternalStorageDirectory(), "A_YingXiongMao/Pic");
    public static final File VIDEO_CACHE = new File(Environment.getExternalStorageDirectory(), "A_YingXionMao/Video");

}
