package liufantech.com.yingxiongmao.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.ContentFragment;
import liufantech.com.yingxiongmao.content.cache.SDcardCache;
import liufantech.com.yingxiongmao.custom.base.BaseFragment;
import liufantech.com.yingxiongmao.custom.base.NetworkUtil;

public class MainActivity extends AppCompatActivity implements ContentFragment.OnFloatingActionButtonClicked {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NetworkUtil mNetworkUtil;

    private RootFragment fragmentRoot;
    private Handler mHandler;
    private SDcardCache mSDcardCache;

    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSDcardCache = SDcardCache.get(this);

        fragmentRoot = RootFragment.getInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragmentRoot).commit();
        }

        System.out.println("=========================================MainActivity onCreate");

        mHandler = new Handler();

        mNetworkUtil = NetworkUtil.getInstance(getApplicationContext());

        if (!NetworkUtil.getInstance().isNetworkConnected()) {
            System.out.println("MainActivity=====================网络没有连接======================");
        }

        initAVOSCloud();
    }

    public void initAVOSCloud() {
        // 跟踪统计应用的打开情况
        AVAnalytics.trackAppOpened(getIntent());
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
    public void onBackPressed() {
        if (fragmentRoot.getDrawerLayout().isDrawerOpen(Gravity.LEFT)) {
            fragmentRoot.getDrawerLayout().closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();

            if (isShouldHideInput(view, event)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
        // 必不可少，否则所有的组件都不会有TouchEvent了
        // 下面这段代码照着网上抄的，如果有问题再进行相应的修改
//        if (getWindow().superDispatchTouchEvent(event)) {
//            return true;
//        }
//        return onTouchEvent(event);
    }

    public boolean isShouldHideInput(View view, MotionEvent event) {
        if ((view != null) && (view instanceof EditText)) {
            int[] leftTop = {0, 0};

            //获取输入框当前的location位置
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if ((event.getX() > left) && (event.getX() < right)
                    && (event.getY() > top) && (event.getY() < bottom)) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("=========================================MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("=========================================MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("=========================================MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("=========================================MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("=========================================MainActivity onDestroy");
    }

    @Override
    public void onFloatingActionButtonClicked(RecyclerView recyclerView) {

    }

    public RootFragment getFragmentRoot() {
        return fragmentRoot;
    }
}
