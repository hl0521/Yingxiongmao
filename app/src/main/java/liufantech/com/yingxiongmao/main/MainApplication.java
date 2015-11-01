package liufantech.com.yingxiongmao.main;

import android.app.Application;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;

/**
 * Created by HL0521 on 2015/10/15.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
//        AVOSCloud.useAVCloudUS();
        AVOSCloud.initialize(this, MainConstant.APP_ID, MainConstant.APP_KEY);

        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    // 关联  installationId 到用户表等操作
                    System.out.println("Save successfully");
                } else {
                    // 保存失败，输出错误信息
                    System.out.println("Save failed");
                }
            }
        });


        // LeanCLoud Test
        AVObject yingxiongmao = new AVObject("Yingxiongmao");
        yingxiongmao.put("HomePage", "homepage");
        yingxiongmao.put("NBA", "nba");
        yingxiongmao.put("Football", "football");
        yingxiongmao.put("Fitness", "fitness");
        yingxiongmao.put("Tennis", "tennis");
        yingxiongmao.put("Beauty", "beauty");
//        yingxiongmao.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    System.out.println("Yingxiongmao: Successfully");
//                } else {
//                    System.out.println("Yingxiongmao: Failed");
//                }
//            }
//        });
    }
}
