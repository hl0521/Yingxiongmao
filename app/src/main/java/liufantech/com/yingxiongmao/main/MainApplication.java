package liufantech.com.yingxiongmao.main;

import android.app.Application;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by HL0521 on 2015/10/15.
 */
public class MainApplication extends Application {

    public static AVUser mAVUser;

    @Override
    public void onCreate() {
        super.onCreate();

        // 如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        AVOSCloud.initialize(this, MainConstant.APP_ID, MainConstant.APP_KEY);

        initAVuser();

    }

    public void initAVuser() {
        mAVUser = AVUser.getCurrentUser();

        if (mAVUser != null) {
            System.out.println("当前缓存的用户信息为：" + mAVUser.getUsername());
        } else {
            System.out.println("当前磁盘无缓存信息，需要用户登陆");
        }
    }

}
