package liufantech.com.yingxiongmao.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.UpdatePasswordCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.manager.UtilityClass;
import liufantech.com.yingxiongmao.custom.widget.ClearEditText;
import liufantech.com.yingxiongmao.main.MainActivity;
import liufantech.com.yingxiongmao.main.MainApplication;

/**
 * Created by HL0521 on 2015/10/31.
 */
public class RegisterFragment extends Fragment {
    private Context mContext;
    private Toolbar mToolbar;

    private ClearEditText mRegisterPhone;
    private ClearEditText mRegisterSetCode;
    private Button mRegisterGetCode;
    private ClearEditText mRegisterSetPassword;
    private ClearEditText mRegisterConfirmPassword;
    private Button mRegisterButton;
    private TextView mRegisterLogin;

    private ClearEditText.onClearEditTextChanged mClearEditTextChangedListener;

    private String mTelPhone;

    private int curRegisterPhone; // 输入电话号码时，记录光标的位置

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
//        args.putString();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initWidget(view);

        return view;
    }

    protected void initWidget(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.fragment_register);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_white_previous);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"Navigation",Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.main_frame, ((MainActivity) mContext).getFragmentRoot()).commit();
            }
        });


        mRegisterPhone = ((ClearEditText) view.findViewById(R.id.registerPhone));
        mRegisterSetCode = (ClearEditText) view.findViewById(R.id.registerSetCode);
        mRegisterGetCode = (Button) view.findViewById(R.id.registerGetCode);
        mRegisterSetPassword = (ClearEditText) view.findViewById(R.id.registerSetPassword);
        mRegisterConfirmPassword = (ClearEditText) view.findViewById(R.id.registerConfirmPassword);
        mRegisterButton = (Button) view.findViewById(R.id.registerButton);
        mRegisterLogin = (TextView) view.findViewById(R.id.registerLogin);

        mClearEditTextChangedListener = new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                setmRegisterButtonAction();
            }
        };

        // 获取验证码
        mRegisterGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });

        mRegisterSetCode.setOnClearEditTextChangedListener(mClearEditTextChangedListener);
        mRegisterSetPassword.setOnClearEditTextChangedListener(mClearEditTextChangedListener);
        mRegisterConfirmPassword.setOnClearEditTextChangedListener(mClearEditTextChangedListener);
        mRegisterPhone.setOnClearEditTextChangedListener(new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                setmRegisterButtonAction();

                setTelNumberShownFormat(text, start, lengthBefore, lengthAfter);
            }
        });

        // 用户注册
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        mRegisterButton.setClickable(false);
        mRegisterButton.setBackgroundColor(Color.parseColor("#CCCCCC"));

        mRegisterLogin.setClickable(true);
        mRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }


    /**
     * 注册时，获取验证码
     * 正常情况下，获取验证码双后，就默认注册了一个账户，用户名和密码都是手机号，如果用户放弃了注册，这个
     * 手机号对应的账户也是存在的，后台并没有删除
     *
     * @return false 验证码获取失败
     * true  验证码获取成功
     */

    private boolean getCode() {
        if (UtilityClass.getInstance().isPhoneNumber(mTelPhone)) {
            // 由于 LeanCloud 要求用户先注册，再验证手机号，因此需要先生成一个默认的账户
            if (MainApplication.mAVUser == null) {
                MainApplication.mAVUser = new AVUser();
            }
            MainApplication.mAVUser.setUsername(mTelPhone);
            MainApplication.mAVUser.setPassword(mTelPhone);
            MainApplication.mAVUser.setFetchWhenSave(true);
            // 注册时如果提供了手机号码，LeanCloud会自动发送一条验证码
//            newUser.setMobilePhoneNumber(mTelPhone);

            MainApplication.mAVUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {

                    if (e == null) {  // 默认注册成功
                        System.out.println("默认注册成功");
                        // 发送手机验证码
                        sendMobilePhoneSmsCode();
                    } else {
                        if (e.getCode() == AVException.USERNAME_TAKEN) {  // 当前手机号已经被注册
                            // 验证当前手机号默认密码能否登陆，如果可以，则说明此账户是刚创建的
                            AVUser.logInInBackground(mTelPhone, mTelPhone, new LogInCallback<AVUser>() {
                                @Override
                                public void done(AVUser user, AVException e) {
                                    if (user != null) {
                                        // 发送手机验证码
                                        MainApplication.mAVUser = user;
                                        sendMobilePhoneSmsCode();
                                    } else {
                                        Toast.makeText(mContext, "该手机号已注册", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            System.out.println(e);
                        }
                    }

                }
            });
        } else {
            Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void sendMobilePhoneSmsCode() {

        if (MainApplication.mAVUser.getMobilePhoneNumber() == null) {
            MainApplication.mAVUser.setMobilePhoneNumber(mTelPhone);

        }
        if (!MainApplication.mAVUser.getMobilePhoneNumber().contentEquals(mTelPhone)) {
            MainApplication.mAVUser.setMobilePhoneNumber(mTelPhone);
        }

        MainApplication.mAVUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    AVUser.requestMobilePhoneVerifyInBackground(mTelPhone, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {  // 操作成功
                                System.out.println("验证码已发送，请注意查收");
                                Toast.makeText(mContext, "验证码已发送，请注意查收", Toast.LENGTH_SHORT).show();
                            } else {
                                // TODO: 2015/12/10 如果发送失败，待处理
                                if (e.getCode() == 601) {
                                    Toast.makeText(mContext, "发送短信过快，请稍后再试", Toast.LENGTH_SHORT).show();
                                    System.out.println("发送短信过快，请稍后再试");
                                } else {
                                    Toast.makeText(mContext, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                    System.out.println("验证码发送失败");
                                }
                                System.out.println(e);
                            }
                        }
                    });
                } else {
                    System.out.println(e);
                }
            }
        });
    }

    /**
     * 基于 LeanCloud 云端进行注册
     *
     * @return false 注册失败
     * true  注册流程走完了，并不一定注册成功，可以由于验证码错误注册失败
     */
    private boolean register() {

        final String s1 = mRegisterSetPassword.getText().toString();
        String s2 = mRegisterConfirmPassword.getText().toString();

        if (!(s1.contentEquals(s2))) {
            Toast.makeText(mContext, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        AVUser.verifyMobilePhoneInBackground(mRegisterSetCode.getText().toString()
                , new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {  // 验证码输入正确
                    MainApplication.mAVUser.updatePasswordInBackground(mTelPhone, s1, new UpdatePasswordCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                registerSuccessful();
                            } else {
                                System.out.println(e);
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "手机验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;
    }

    /**
     * 注册成功，提示操作
     */
    private void registerSuccessful() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("注册成功");
        builder.setMessage("恭喜你，注册成功！");
        builder.setNegativeButton("完善资料", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "开发中，敬请期待！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("返回首页", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction().replace(R.id.main_frame
                        , ((MainActivity) mContext).getFragmentRoot()).commit();
            }
        });
        builder.show();
    }

    /**
     * 设置 mRegisterButton 的动作反馈
     */
    private void setmRegisterButtonAction() {
        if (mRegisterPhone.isEmpty() || mRegisterSetCode.isEmpty() ||
                mRegisterSetPassword.isEmpty() || mRegisterConfirmPassword.isEmpty()) {
            mRegisterButton.setClickable(false);
            mRegisterButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            mRegisterButton.setClickable(true);
            mRegisterButton.setBackgroundResource(R.drawable.login_button_selector);
        }
    }

    /**
     * 输入手机号码时，自动设置手机号码显示格式为 000-0000-0000，以便于观看
     * 参数意义参见 android.text.TextWatcher
     *
     * @param text         当前显示的文本
     * @param start        当前显示的文本与以前的文本相比，第一个出现变化的字符位置
     * @param lengthBefore 当前显示的文本中，<code>start</code>开始，连续<code>lengthBefore</code>个字
     *                     符，是被修改的字符
     * @param lengthAfter  当前显示的文本中，<code>start</code>开始，连续<code>lengthAfter</code>个字
     *                     符，是新加入的字符
     */
    private void setTelNumberShownFormat(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        mTelPhone = text.toString().replace("-", "");

        System.out.println("mTelPhone = " + mTelPhone);

        System.out.println("start=" + start + " /// lengthBefore=" + lengthBefore
                + " /// lengthAfter=" + lengthAfter);
        System.out.println("============================================================");

        StringBuffer stringBuffer = new StringBuffer(mTelPhone);

        if (lengthAfter >= 1) {   // >=1 表示修改的字符超过1个，如果 lengthAfter==0，则说明此时只是删除了字符
            if (stringBuffer.length() >= 3) {
                if (stringBuffer.length() == 3) {
                    stringBuffer.append('-');
                } else {
                    if (stringBuffer.charAt(3) != '-') {
                        stringBuffer.insert(3, '-');
                    }
                    if (stringBuffer.length() >= 8) {
                        if (stringBuffer.length() == 8) {
                            stringBuffer.append('-');
                        } else {
                            if (stringBuffer.charAt(8) != '-') {
                                stringBuffer.insert(8, '-');
                            }
                            if (stringBuffer.length() > 13) {
                                stringBuffer.delete(13, stringBuffer.length());
                            }
                        }
                    }
                }

                if (!(stringBuffer.toString().contentEquals(text))) {
                    mRegisterPhone.setText(stringBuffer);
                    // 将光标移动到最后，否则光标会在最前面
                    // TODO: 2015/12/9 这个地方光标的显示，还是有一点问题的，这里总是将光标设置到最后面，需要改进
                    mRegisterPhone.setSelection(mRegisterPhone.getText().length());
                }
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
