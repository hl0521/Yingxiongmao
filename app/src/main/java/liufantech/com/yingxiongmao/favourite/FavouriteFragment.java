package liufantech.com.yingxiongmao.favourite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.abstraction.BaseFragment;
import liufantech.com.yingxiongmao.main.MainActivity;

/**
 * Created by HL0521 on 2015/11/7.
 */
public class FavouriteFragment extends BaseFragment {

    private Context mContext;

    private Toolbar mToolbar;

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        initWidget(view);

        return view;
    }

    protected void initWidget(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.drawer_favourite);
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
