package liufantech.com.yingxiongmao.setting;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URL;

import liufantech.com.yingxiongmao.R;
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
    private VideoView videoView;
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

        videoView = (VideoView) view.findViewById(R.id.videoView);
        button = (Button) view.findViewById(R.id.button);

        // 使用这种方式创建的 MediaController 没有 快进 和 快退 两个按钮
        MediaController mediaController = new MediaController(mContext, false);

        videoView.setMediaController(mediaController);
//        mediaController.setMediaPlayer(videoView);

//        String url = MainConstant.VIDEO_CACHE + "/asdfg.mp4";
        String url = "sdcard/A_YingXiongMao/Video/asdfg.mp4";
//        String url = "http://v.youku.com/v_show/id_XMTQyMzI5NTAwMA==.html?firsttime=29";
//        System.out.println(url);
        videoView.setVideoPath(url);
//        videoView.setVideoURI(Uri.parse(url));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
                videoView.requestFocus();
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
