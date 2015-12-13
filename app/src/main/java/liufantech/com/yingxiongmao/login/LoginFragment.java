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
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.widget.ClearEditText;
import liufantech.com.yingxiongmao.main.MainActivity;
import liufantech.com.yingxiongmao.main.MainApplication;
import liufantech.com.yingxiongmao.main.RootFragment;

public class LoginFragment extends Fragment {

    private Context mContext;

    private ClearEditText mLoginAccount;
    private ClearEditText mLoginPassword;
    private Button mLoginButton;
    private TextView mLoginProblem;
    private TextView mLoginRegister;
    private ClearEditText.onClearEditTextChanged mClearEditTextChangedListener;

    private Toolbar mToolbar;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initWidget(view);

        return view;
    }

    protected void initWidget(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.drawer_login);
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
        // hide the menu
//        setMenuVisibility(false);

        mLoginAccount = (ClearEditText) view.findViewById(R.id.loginAccount);
        mLoginPassword = (ClearEditText) view.findViewById(R.id.loginPassword);
        mLoginButton = (Button) view.findViewById(R.id.loginButton);
        mLoginProblem = (TextView) view.findViewById(R.id.loginProblem);
        mLoginRegister = (TextView) view.findViewById(R.id.loginRegister);

        mClearEditTextChangedListener = new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
                setmLoginButtonAction();
            }

        };

        mLoginAccount.setOnClearEditTextChangedListener(mClearEditTextChangedListener);
        mLoginPassword.setOnClearEditTextChangedListener(mClearEditTextChangedListener);

        mLoginProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Login Problem", Toast.LENGTH_SHORT).show();
                forgetPassword();
            }
        });

        mLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterPage();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mLoginButton.setClickable(false);
        mLoginButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mLoginProblem.setClickable(true);
        mLoginRegister.setClickable(true);
    }


    /**
     * 设置 mLoginButton 的动作反馈
     */
    private void setmLoginButtonAction() {
        if ((mLoginAccount.isEmpty()) || (mLoginPassword.isEmpty())) {
            mLoginButton.setClickable(false);
            mLoginButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        } else {
            mLoginButton.setClickable(true);
            // 设置 手按下按键 / 离开按键 时，按键的背景颜色提示
            mLoginButton.setBackgroundResource(R.drawable.login_button_selector);
        }
    }

    private void login() {
        AVUser.logInInBackground(mLoginAccount.getText().toString(), mLoginPassword.getText().toString()
                , new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser user, AVException e) {
                if (user != null) { // 登陆成功
                    RootFragment fragment = RootFragment.getInstance();
                    fragment.getNavigationView().getMenu().getItem(0).setTitle("注销");
                    fragment.getUserName().setText(user.getUsername());
                    MainApplication.mAVUser = user;
                    Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        getFragmentManager().popBackStackImmediate(RootFragment.class.getName()
                                , FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_frame, RootFragment.getInstance()).commit();
                } else { // 登陆失败
                    System.out.println(e);
                    switch (e.getCode()) {
                        case 211:
                            Toast.makeText(mContext, "用户名不存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 210:
                            Toast.makeText(mContext, "用户名和密码不匹配", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void forgetPassword() {
        getFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.main_frame, ForgetPasswordFragment.newinstance()).commit();
    }

    private void openRegisterPage() {
        getFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.main_frame, RegisterFragment.newInstance()).commit();
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