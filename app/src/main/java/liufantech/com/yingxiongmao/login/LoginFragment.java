package liufantech.com.yingxiongmao.login;

import android.content.Context;
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

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.widget.ClearEditText;
import liufantech.com.yingxiongmao.main.MainActivity;

public class LoginFragment extends Fragment {

    private Context mContext;

    private ClearEditText mLoginAccount;
    private ClearEditText mLoginPassword;
    private Button mLoginButton;
    private TextView mLoginProblem;
    private TextView mLoginMore;
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

    public void initWidget(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.app_name);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_white_previous);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"Navigation",Toast.LENGTH_SHORT).show();
//                getFragmentManager().popBackStack();
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_frame, ((MainActivity) mContext).getFragmentRoot()).commit();
            }
        });

        mLoginAccount = (ClearEditText) view.findViewById(R.id.loginAccount);
        mLoginPassword = (ClearEditText) view.findViewById(R.id.loginPassword);
        mLoginButton = (Button) view.findViewById(R.id.loginButton);
        mLoginProblem = (TextView) view.findViewById(R.id.loginProblem);
        mLoginMore = (TextView) view.findViewById(R.id.loginMore);

        mClearEditTextChangedListener = new ClearEditText.onClearEditTextChanged() {
            @Override
            public void onClearEditTextChanged() {
                if ((mLoginAccount.isEmpty()) || (mLoginPassword.isEmpty())) {
                    mLoginButton.setClickable(false);
                    mLoginButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                } else {
                    mLoginButton.setClickable(true);
                    mLoginButton.setBackgroundColor(Color.parseColor("#3F51B5"));
                }
            }
        };

        mLoginButton.setClickable(false);
        mLoginProblem.setClickable(true);
        mLoginMore.setClickable(true);

        mLoginAccount.setOnClearEditTextChangedListener(mClearEditTextChangedListener);
        mLoginPassword.setOnClearEditTextChangedListener(mClearEditTextChangedListener);

        mLoginProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Login Problem", Toast.LENGTH_SHORT).show();
            }
        });

        mLoginMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Login More", Toast.LENGTH_SHORT).show();
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Login Button", Toast.LENGTH_SHORT).show();
            }
        });
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