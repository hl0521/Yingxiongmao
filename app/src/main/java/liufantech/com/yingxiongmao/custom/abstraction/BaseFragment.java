package liufantech.com.yingxiongmao.custom.abstraction;

import android.support.v4.app.Fragment;

/**
 * Created by HL0521 on 2015/11/5.
 */
public abstract class BaseFragment extends Fragment {

    private String mFragmentName;

    public String getFragmentName() {
        return mFragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.mFragmentName = fragmentName;
    }

    public abstract boolean onBackPressed();

}
