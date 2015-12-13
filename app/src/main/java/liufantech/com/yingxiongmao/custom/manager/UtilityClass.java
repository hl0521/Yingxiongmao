package liufantech.com.yingxiongmao.custom.manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HL0521 on 2015/12/13.
 */
public class UtilityClass {
    private static final String TAG = "UtilitiClass";

    private static UtilityClass mInstance;

    private UtilityClass() {

    }

    public static UtilityClass getInstance() {
        if (mInstance == null) {
            mInstance = new UtilityClass();
        }

        return mInstance;
    }

    /**
     * 以下号码总结并不一定是完整的
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     *
     * @param phoneNumber CharSequence，11位手机号码
     * @return true 正常的手机号码格式
     * false 非正常手机号码格式
     */
    public boolean isPhoneNumber(CharSequence phoneNumber) {
        // 以下正则表达式是一个简化的，有待进一步完善
        String phoneRegex = "[1][358][0-9]{9}";
        Pattern pattern = Pattern.compile(phoneRegex);

        if (phoneNumber != null) {
            Matcher matcher = pattern.matcher(phoneNumber);
            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }
}
