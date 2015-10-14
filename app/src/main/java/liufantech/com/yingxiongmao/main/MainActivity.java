package liufantech.com.yingxiongmao.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import liufantech.com.yingxiongmao.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFloatingActionButtonClicked {

    private String[] mCategory;

    private MainFragment fragmentHomePage;
    private MainFragment fragmentNBA;
    private MainFragment fragmentFootball;
    private MainFragment fragmentFitness;
    private MainFragment fragmentTennis;
    private MainFragment fragmentBeauty;

    private Toolbar mToolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;

    private TabLayout mTabLayout;
    //    private TabLayout.TabLayoutOnPageChangeListener mListener;
    private ViewPager mViewPager;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private ArrayList<Fragment> mArrayList;
    private int mCurrentFragmentPosition;

    private FloatingActionButton mFloatingActionButton;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        initToolbar();

        initViewPager();

        initTabLayout();

        initDrawerLayout();

        initFloatingActionButton();
    }

    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // App logo
//        mToolbar.setLogo(R.mipmap.ic_launcher);
        // Title
        mToolbar.setTitle(R.string.app_name);
        // Sub Title
//        toolbar.setSubtitle(R.string.app_subtitle);
        setSupportActionBar(mToolbar);
        // Navigation Icon 要设定在 setSupoortActionBar 才有作用，否则会出现 back button
        mToolbar.setNavigationIcon(R.drawable.ic_menu);

        onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                return true;
            }
        };
        mToolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void initViewPager() {
        mArrayList = new ArrayList<>();

        fragmentHomePage = MainFragment.newInstance(MainConstant.CATEGORY_HOMEPAGE);
        fragmentNBA = MainFragment.newInstance(MainConstant.CATEGORY_NBA);
        fragmentFootball = MainFragment.newInstance(MainConstant.CATEGORY_FOOTBALL);
        fragmentFitness = MainFragment.newInstance(MainConstant.CATEGORY_FITNESS);
        fragmentTennis = MainFragment.newInstance(MainConstant.CATEGORY_TENNIS);
        fragmentBeauty = MainFragment.newInstance(MainConstant.CATEGORY_BEAUTY);

        mArrayList.add(fragmentHomePage);
        mArrayList.add(fragmentNBA);
        mArrayList.add(fragmentFootball);
        mArrayList.add(fragmentFitness);
        mArrayList.add(fragmentTennis);
        mArrayList.add(fragmentBeauty);

        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mArrayList, this);

        mViewPager = (ViewPager) findViewById(R.id.contentViewPager);
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
    }

    public void initTabLayout() {
        mCategory = getResources().getStringArray(R.array.category);
        mTabLayout = (TabLayout) findViewById(R.id.tabCategory);

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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
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

        mNavigationView = (NavigationView) findViewById(R.id.main_navigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.signin:
                        Toast.makeText(getApplicationContext(), "signin", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.homepage:
                        Toast.makeText(getApplicationContext(), "homepage", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.favourite:
                        Toast.makeText(getApplicationContext(), "favourite", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.back:
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        finish();
                        System.exit(0);
                    default:
                }

                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public void initFloatingActionButton() {
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Current page: " + mViewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFloatingActionButtonClicked(RecyclerView recyclerView) {

    }
}
