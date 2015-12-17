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
import com.avos.avoscloud.UpdatePasswordCallback;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.widget.ClearEditText;
import liufantech.com.yingxiongmao.main.MainActivity;

/**
 * Created by HL0521 on 2015/12/13.
 */
public class ResetPasswordFragment extends Fragment {
    private static final String TAG = "ResetPasswordFragment";
    private Context mContext;
    private ClearEditText mVerifyCode;
    private ClearEditText mNewPassword1;
    private ClearEditText mNewPassword2;
    private Button mSubmitButton;

    private Toolbar mToolbar;

    public ResetPasswordFragment() {

    }

    public static ResetPasswordFragment newinstance() {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_resetpassword, container, false);

        initWidget(view);

        return view;
    }

    private void initWidget(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.reset_password);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_white_previous);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mVerifyCode = (ClearEditText) view.findViewById(R.id.verifyCode);
        mNewPassword1 = (ClearEditText) view.findViewById(R.id.setPassword);
        mNewPassword2 = (ClearEditText) view.findViewById(R.id.confirmPassword);
        mSubmitButton = (Button) view.findViewById(R.id.submitButton);

        ClearEditText.onClearEditTextChanged listener = new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                setmLoginButtonAction();
            }
        };

        mVerifyCode.setOnClearEditTextChangedListener(listener);
        mNewPassword1.setOnClearEditTextChangedListener(listener);
        mNewPassword2.setOnClearEditTextChangedListener(listener);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        mSubmitButton.setClickable(false);
        mSubmitButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
    }

    /**
     * 设置 mLoginButton 的动作反馈
     */
    private void setmLoginButtonAction() {
        if (mVerifyCode.isEmpty() || mNewPassword1.isEmpty() || mNewPassword2.isEmpty()) {
            mSubmitButton.setClickable(false);
            mSubmitButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            mSubmitButton.setClickable(true);
            // 设置 手按下按键 / 离开按键 时，按键的背景颜色提示
            mSubmitButton.setBackgroundResource(R.drawable.login_button_selector);
        }
    }

    private void submit() {
        String s1 = mNewPassword1.getText().toString();
        String s2 = mNewPassword2.getText().toString();

        if (!(s1.contentEquals(s2))) {
            Toast.makeText(mContext, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        AVUser.resetPasswordBySmsCodeInBackground(mVerifyCode.getText().toString(), s1
                , new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
//                    Toast.makeText(mContext, "密码重置成功", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack("LoginFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    getFragmentManager().beginTransaction().addToBackStack(null)
//                            .replace(R.id.main_frame, LoginFragment.newInstance()).commit();
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
