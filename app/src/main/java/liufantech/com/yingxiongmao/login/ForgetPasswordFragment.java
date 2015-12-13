package liufantech.com.yingxiongmao.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.manager.UtilityClass;
import liufantech.com.yingxiongmao.custom.widget.ClearEditText;
import liufantech.com.yingxiongmao.main.MainActivity;

/**
 * Created by HL0521 on 2015/12/13.
 */
public class ForgetPasswordFragment extends Fragment {

    private Context mContext;
    private ClearEditText mMobilePhoneInput;
    private Button mGetCodeButton;

    private Toolbar mToolbar;

    public ForgetPasswordFragment() {

    }

    public static ForgetPasswordFragment newinstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_forgetpassword, container, false);

        initWidget(view);

        return view;
    }

    private void initWidget(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.forget_password);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_white_previous);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mMobilePhoneInput = (ClearEditText) view.findViewById(R.id.mobilePhone);
        mGetCodeButton = (Button) view.findViewById(R.id.getVerifyCode);

        mMobilePhoneInput.setOnClearEditTextChangedListener(new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                setmLoginButtonAction();
            }
        });

        mGetCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerifyCode();
            }
        });
        mGetCodeButton.setClickable(false);
        mGetCodeButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
    }

    /**
     * 设置 mLoginButton 的动作反馈
     */
    private void setmLoginButtonAction() {
        if (mMobilePhoneInput.isEmpty() ||
                !(UtilityClass.getInstance().isPhoneNumber(mMobilePhoneInput.getText().toString()))) {
            mGetCodeButton.setClickable(false);
            mGetCodeButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            mGetCodeButton.setClickable(true);
            // 设置 手按下按键 / 离开按键 时，按键的背景颜色提示
            mGetCodeButton.setBackgroundResource(R.drawable.login_button_selector);
        }
    }

    private void getVerifyCode() {
        AVUser.requestPasswordResetBySmsCodeInBackground(mMobilePhoneInput.getText().toString()
                , new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Toast.makeText(mContext, "验证码已发送", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.main_frame, ResetPasswordFragment.newinstance()).commit();
                } else {
                    System.out.println(e.toString());

                    switch (e.getCode()) {
                        case 0:
                            break;
                        case 1:
                            break;
                        default:
                    }
                }
            }
        });
    }

}
