package liufantech.com.yingxiongmao.main;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.login.LoginFragment;

/**
 * Created by HL0521 on 2015/10/31.
 */
public class RootFragment extends Fragment {

    private String[] mCategory;

    private ContentFragment fragmentHomePage;
    private ContentFragment fragmentNBA;
    private ContentFragment fragmentFootball;
    private ContentFragment fragmentFitness;
    private ContentFragment fragmentTennis;
    private ContentFragment fragmentBeauty;

    private LoginFragment mLoginFragment;

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

        mLoginFragment = LoginFragment.newInstance();

        initToolbar(view);

        initViewPager(view);

        initTabLayout(view);

        initDrawerLayout();

        initFloatingActionButton(view);

        System.out.println("=========================================RootFragment onCreateView");

        return view;
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

        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void initViewPager(View view) {
        mArrayList = new ArrayList<>();

        fragmentHomePage = ContentFragment.newInstance(MainConstant.CATEGORY_HOMEPAGE);
        fragmentNBA = ContentFragment.newInstance(MainConstant.CATEGORY_NBA);
        fragmentFootball = ContentFragment.newInstance(MainConstant.CATEGORY_FOOTBALL);
        fragmentFitness = ContentFragment.newInstance(MainConstant.CATEGORY_FITNESS);
        fragmentTennis = ContentFragment.newInstance(MainConstant.CATEGORY_TENNIS);
        fragmentBeauty = ContentFragment.newInstance(MainConstant.CATEGORY_BEAUTY);

        mArrayList.add(fragmentHomePage);
        mArrayList.add(fragmentNBA);
        mArrayList.add(fragmentFootball);
        mArrayList.add(fragmentFitness);
        mArrayList.add(fragmentTennis);
        mArrayList.add(fragmentBeauty);

        mMainViewPagerAdapter = new MainViewPagerAdapter(getActivity().getSupportFragmentManager(), mArrayList, mContext);

        mViewPager = (ViewPager) view.findViewById(R.id.contentViewPager);
        mViewPager.setAdapter(mMainViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
        mCategory = getResources().getStringArray(R.array.category);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabCategory);

        for (int i = 0; i < mCategory.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mCategory[i]));
        }

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setCurrentItem(0);  // default: the first category
    }

    public void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.main_drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
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
                        getFragmentManager().beginTransaction().addToBackStack(null)
                                .replace(R.id.main_frame, mLoginFragment).commit();
                        break;
                    case R.id.homepage:
                        Toast.makeText(mContext, "homepage", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, ((MainActivity) mContext).getFragmentRoot()).commit();
                        break;
                    case R.id.favourite:
                        Toast.makeText(mContext, "favourite", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(mContext, "setting", Toast.LENGTH_SHORT).show();
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
}

