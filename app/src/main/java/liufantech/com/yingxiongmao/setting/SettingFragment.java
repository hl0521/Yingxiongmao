package liufantech.com.yingxiongmao.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.DownloadImageTask;
import liufantech.com.yingxiongmao.custom.base.BaseFragment;
import liufantech.com.yingxiongmao.main.MainActivity;
import liufantech.com.yingxiongmao.main.MainConstant;

/**
 * Created by HL0521 on 2015/11/7.
 */
public class SettingFragment extends BaseFragment {

    private Context mContext;

    private Toolbar mToolbar;

    // Following widgets used for test
    private ImageView imageView;
    private Button button;

    public SettingFragment() {

    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        initWidget(view);

        imageView = (ImageView) view.findViewById(R.id.imageTemp);
        button = (Button) view.findViewById(R.id.buttonTemp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                downloadImageTask.execute(MainConstant.PIC_URL1);
            }
        });

        return view;
    }

    protected void initWidget(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.drawer_setting);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.icon_white_previous);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }
}
