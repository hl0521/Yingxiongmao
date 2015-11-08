package liufantech.com.yingxiongmao.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.custom.abstraction.BaseFragment;
import liufantech.com.yingxiongmao.favourite.FavouriteFragment;
import liufantech.com.yingxiongmao.login.LoginFragment;
import liufantech.com.yingxiongmao.setting.SettingFragment;

/**
 * Created by HL0521 on 2015/10/31.
 */
public class RootFragment extends BaseFragment {

    private String[] mCategory;

    private ContentFragment fragmentHomePage;
    private ContentFragment fragmentNBA;
    private ContentFragment fragmentFootball;
    private ContentFragment fragmentFitness;
    private ContentFragment fragmentTennis;
    private ContentFragment fragmentBeauty;

    private LoginFragment mLoginFragment;
    private SettingFragment mSettingFragment;
    private FavouriteFragment mFavouriteFragment;

    private Toolbar mToolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    private TabLayout mTabLayout;
    //    private TabLayout.TabLayoutOnPageChangeListener mListener;
    private ViewPager mViewPager;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ArrayList<Fragment> mArrayList;
    private int mCurrentFragmentPosition;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private FloatingActionButton mFloatingActionButton;

    private Context mContext;
    private Handler mHandler;

    public RootFragment() {
        // required empty public constructor
    }

    public static RootFragment newInstance() {
        RootFragment fragment = new RootFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initWidget();

        initToolbar(view);

        initViewPager(view);

        initTabLayout(view);

        initDrawerLayout();

        initFloatingActionButton(view);

        System.out.println("=========================================RootFragment onCreateView");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("=========================================RootFragment onActivityCreated");
    }

    public void initWidget() {
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
        }

        if (mSettingFragment == null) {
            mSettingFragment = SettingFragment.newInstance();
        }

        if (mFavouriteFragment == null) {
            mFavouriteFragment = FavouriteFragment.newInstance();
        }
        if (mHandler == null) {
            mHandler = new Handler();
        }
    }

    public void initToolbar(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.app_name);
        // Sub Title
//        toolbar.setSubtitle(R.string.app_subtitle);
        ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        // Navigation Icon 要设定在 setSupoortActionBar 才有作用，否则会出现 back button
        mToolbar.setNavigationIcon(R.drawable.ic_menu);

        if (onMenuItemClickListener == null) {
            onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_search:
                            Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }

                    return true;
                }
            };
        }

        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void initViewPager(View view) {
        if (mArrayList == null) {
            mArrayList = new ArrayList<>();
        }

        if (fragmentHomePage == null) {
            fragmentHomePage = ContentFragment.newInstance(MainConstant.CATEGORY_HOMEPAGE);
            mArrayList.add(fragmentHomePage);
        }
        if (fragmentNBA == null) {
            fragmentNBA = ContentFragment.newInstance(MainConstant.CATEGORY_NBA);
            mArrayList.add(fragmentNBA);
        }
        if (fragmentFootball == null) {
            fragmentFootball = ContentFragment.newInstance(MainConstant.CATEGORY_FOOTBALL);
            mArrayList.add(fragmentFootball);
        }
        if (fragmentFitness == null) {
            fragmentFitness = ContentFragment.newInstance(MainConstant.CATEGORY_FITNESS);
            mArrayList.add(fragmentFitness);
        }
        if (fragmentTennis == null) {
            fragmentTennis = ContentFragment.newInstance(MainConstant.CATEGORY_TENNIS);
            mArrayList.add(fragmentTennis);
        }
        if (fragmentBeauty == null) {
            fragmentBeauty = ContentFragment.newInstance(MainConstant.CATEGORY_BEAUTY);
            mArrayList.add(fragmentBeauty);
        }

        System.out.println("===========AAAAAAAAAAA===========" + mArrayList.size());

        if (mMainViewPagerAdapter == null) {
            mMainViewPagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(),
                    mArrayList, mContext);
        }

        mViewPager = (ViewPager) view.findViewById(R.id.contentViewPager);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixel) {
                mTabLayout.setScrollPosition(position, positionOffset, true);
                mCurrentFragmentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mViewPager.setCurrentItem(2);
    }

    public void initTabLayout(View view) {
        if (mCategory == null) {
            mCategory = getResources().getStringArray(R.array.category);
        }
        mTabLayout = (TabLayout) view.findViewById(R.id.tabCategory);
        for (int i = 0; i < mCategory.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mCategory[i]));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
//                System.out.println("*************点击了TAB"+tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//                System.out.println("***************再次点击TAB"+tab.getPosition());
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }
        });

//        mViewPager.setCurrentItem(2,false);  // default: the first category
    }

    public void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.main_drawer);
//        ImageView imageView = (ImageView) getActivity().findViewById(R.id.user_portrait);
//        TextView textView = (TextView) getActivity().findViewById(R.id.user_name);
//
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.parseColor("#FFFFFF"));
//        paint.setStyle(Paint.Style.STROKE);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
//        Canvas canvas = new Canvas(bitmap);                                 // bitmap就是我们原来的图,比如头像
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));  //因为我们先画了图所以DST_IN
//        int radius = bitmap.getWidth();                                     //假设图片是正方形的
//        canvas.drawCircle(radius, radius, radius, paint);                   //r=radius, 圆心(r,r)

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                Toast.makeText(getApplicationContext(), "Drawer opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                Toast.makeText(getApplicationContext(), "Drawer closed", Toast.LENGTH_SHORT).show();
            }
        };
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mNavigationView = (NavigationView) getActivity().findViewById(R.id.main_navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.signin:
//                        Toast.makeText(mContext, "signin", Toast.LENGTH_SHORT).show();
                        if (mLoginFragment.isVisible() == false) {
                            getFragmentManager().beginTransaction().addToBackStack(null)
                                    .replace(R.id.main_frame, mLoginFragment).commit();
                        }
                        break;
                    case R.id.homepage:
                        Toast.makeText(mContext, "homepage", Toast.LENGTH_SHORT).show();
                        while (getFragmentManager().getBackStackEntryCount() > 0) {
                            getFragmentManager().popBackStackImmediate();
                        }

                        // 下面的代码就执行时，上面的fragment还没初始化完成，代码没有作用，因此此处加了个延时
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                mTabLayout.setScrollPosition(0, 0, true);
                                mViewPager.setCurrentItem(0, false);
                            }
                        }, 10);
//                        getFragmentManager().beginTransaction()
//                                .replace(R.id.main_frame, ((MainActivity) mContext).getFragmentRoot()).commit();
                        break;
                    case R.id.favourite:
//                        Toast.makeText(mContext, "favourite", Toast.LENGTH_SHORT).show();
                        if (mFavouriteFragment.isVisible() == false) {
                            getFragmentManager().beginTransaction().addToBackStack(null)
                                    .replace(R.id.main_frame, mFavouriteFragment).commit();
                        }
                        break;
                    case R.id.setting:
//                        Toast.makeText(mContext, "setting", Toast.LENGTH_SHORT).show();
                        if (mSettingFragment.isVisible() == false) {
                            getFragmentManager().beginTransaction().addToBackStack(null)
                                    .replace(R.id.main_frame, mSettingFragment).commit();
                        }
                        break;
                    case R.id.back:
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        getActivity().finish();
                        System.exit(0);
                    default:
                }

                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public void initFloatingActionButton(View view) {
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Current page: " + mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
                if (mViewPager != null) {
                    switch (mViewPager.getCurrentItem()) {
                        case 0:
                            fragmentHomePage.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                        case 1:
                            fragmentNBA.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                        case 2:
                            fragmentFootball.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                        case 3:
                            fragmentFitness.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                        case 4:
                            fragmentTennis.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                        case 5:
                            fragmentBeauty.getmRecyclerViewLayoutManager().scrollToPosition(0);
                            break;
                    }
                }
            }
        });
    }

    public Toolbar getmToolbar() {
        return mToolbar;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("=========================================RootFragment onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("=========================================RootFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("=========================================RootFragment onDestroy");
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

