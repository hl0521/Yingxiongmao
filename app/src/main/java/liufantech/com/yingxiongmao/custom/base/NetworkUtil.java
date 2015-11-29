package liufantech.com.yingxiongmao.custom.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by HL0521 on 2015/11/28.
 */
public class NetworkUtil {

    private Context mContext;
    private static NetworkUtil mNetworkUtil;

    private NetworkUtil(Context context) {
        mContext = context;
    }

    public static NetworkUtil getInstance(Context context) {
        if (mNetworkUtil == null) {
            synchronized (NetworkUtil.class) {
                if (mNetworkUtil == null) {
                    mNetworkUtil = new NetworkUtil(context);
                }
            }
        }

        return mNetworkUtil;
    }

    public static NetworkUtil getInstance() {
        if (mNetworkUtil != null) {
            return mNetworkUtil;
        }
        return new NetworkUtil(null);
    }

    public boolean isNetworkConnected() {

        if (mContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                boolean wifiConnectionOK = false;
                boolean mobileConnectionOK = false;

                NetworkInfo mWifiNetworkInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
                if (mWifiNetworkInfo != null) {
                    wifiConnectionOK = mWifiNetworkInfo.isConnected();
                }

                NetworkInfo mMobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null) {
                    mobileConnectionOK = mMobileNetworkInfo.isConnected();
                }

                boolean networkConnected = wifiConnectionOK || mobileConnectionOK;

                if (!networkConnected) {
                    System.out.println("===============================网络未连接=======");
                }

                return networkConnected;
            }
        }

        return false;
    }
}
