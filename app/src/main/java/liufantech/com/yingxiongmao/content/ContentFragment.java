package liufantech.com.yingxiongmao.content;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.content.adapter.MainRecyclerViewAdapter;
import liufantech.com.yingxiongmao.custom.base.BaseFragment;
import liufantech.com.yingxiongmao.main.MainConstant;


public class ContentFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATEGORY = "category";

    // TODO: Rename and change types of parameters
    private String mCategory;
    private List<String> mPicUrlList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerViewScrollDetector mRecyclerViewScrollDetector;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private FloatingActionButton mFloatingActionButton;

    private android.os.Handler mHandler;
    private Context mContext;

    private ContentFragment.OnFloatingActionButtonClicked mOnFloatingActionButtonListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @return A new instance of fragment ContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentFragment newInstance(String category) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public ContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        try {
            mOnFloatingActionButtonListener = (OnFloatingActionButtonClicked) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implenment OnFloatingActionButtonListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;

        mHandler = new android.os.Handler();

        initPicUrlList();

        switch (mCategory) {
            case MainConstant.CATEGORY_HOMEPAGE:
                view = inflater.inflate(R.layout.fragment_homepage, container, false);
                System.out.println("=========================================ContentFragment onCreateView 0");
                break;
            case MainConstant.CATEGORY_NBA:
                view = inflater.inflate(R.layout.fragment_nba, container, false);
                System.out.println("=========================================ContentFragment onCreateView 1");
                break;
            case MainConstant.CATEGORY_FOOTBALL:
                view = inflater.inflate(R.layout.fragment_football, container, false);
                System.out.println("=========================================ContentFragment onCreateView 2");
                break;
            case MainConstant.CATEGORY_FITNESS:
                view = inflater.inflate(R.layout.fragment_fitness, container, false);
                System.out.println("=========================================ContentFragment onCreateView 3");
                break;
            case MainConstant.CATEGORY_TENNIS:
                view = inflater.inflate(R.layout.fragment_tennis, container, false);
                System.out.println("=========================================ContentFragment onCreateView 4");
                break;
            case MainConstant.CATEGORY_BEAUTY:
                view = inflater.inflate(R.layout.fragment_beauty, container, false);
                System.out.println("=========================================ContentFragment onCreateView 5");
                break;
            default:
                view = null;
                return view;
        }

        initRecyclerView(view);

        return view;
    }

    public void initPicUrlList() {
        if (mPicUrlList == null) {
            mPicUrlList = new ArrayList<>();
            mPicUrlList.add(MainConstant.PIC_URL0);
            mPicUrlList.add(MainConstant.PIC_URL1);
            mPicUrlList.add(MainConstant.PIC_URL2);
            mPicUrlList.add(MainConstant.PIC_URL3);
            mPicUrlList.add(MainConstant.PIC_URL4);
            mPicUrlList.add(MainConstant.PIC_URL5);
            mPicUrlList.add(MainConstant.PIC_URL6);
            mPicUrlList.add(MainConstant.PIC_URL7);
            mPicUrlList.add(MainConstant.PIC_URL8);
            mPicUrlList.add(MainConstant.PIC_URL9);
        }
    }

    public void initRecyclerView(View view) {
        if (view != null) {

            mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

            mRecyclerViewAdapter = new MainRecyclerViewAdapter(mCategory, mPicUrlList);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);

            mFloatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

            mRecyclerViewScrollDetector = new MyRecyclerViewScrollDetector(mCategory, mFloatingActionButton);
            mRecyclerViewScrollDetector.setmScrollThreshold(15);

            mRecyclerView.addOnScrollListener(mRecyclerViewScrollDetector);

            LoadResourceManager.getInstance().addLoadStateChangedListener(mCategory, mLoadStateChangedListener);
        }
    }

    private LoadResourceManager.LoadStateChangedListener mLoadStateChangedListener =
            new LoadResourceManager.LoadStateChangedListener() {
                @Override
                public void onResumed() {
                    System.out.println("================" + mCategory + "=============");
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }
            };

    @Override
    public void onPause() {
        super.onPause();
        switch (mCategory) {
            case MainConstant.CATEGORY_HOMEPAGE:
                System.out.println("=========================================ContentFragment onPause 0");
                break;
            case MainConstant.CATEGORY_NBA:
                System.out.println("=========================================ContentFragment onPause 1");
                break;
            case MainConstant.CATEGORY_FOOTBALL:
                System.out.println("=========================================ContentFragment onPause 2");
                break;
            case MainConstant.CATEGORY_FITNESS:
                System.out.println("=========================================ContentFragment onPause 3");
                break;
            case MainConstant.CATEGORY_TENNIS:
                System.out.println("=========================================ContentFragment onPause 4");
                break;
            case MainConstant.CATEGORY_BEAUTY:
                System.out.println("=========================================ContentFragment onPause 5");
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        switch (mCategory) {
            case MainConstant.CATEGORY_HOMEPAGE:
                System.out.println("=========================================ContentFragment onDestroy 0");
                break;
            case MainConstant.CATEGORY_NBA:
                System.out.println("=========================================ContentFragment onDestroy 1");
                break;
            case MainConstant.CATEGORY_FOOTBALL:
                System.out.println("=========================================ContentFragment onDestroy 2");
                break;
            case MainConstant.CATEGORY_FITNESS:
                System.out.println("=========================================ContentFragment onDestroy 3");
                break;
            case MainConstant.CATEGORY_TENNIS:
                System.out.println("=========================================ContentFragment onDestroy 4");
                break;
            case MainConstant.CATEGORY_BEAUTY:
                System.out.println("=========================================ContentFragment onDestroy 5");
                break;
            default:
        }
    }

    public RecyclerView.LayoutManager getmRecyclerViewLayoutManager() {
        return mRecyclerViewLayoutManager;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public interface OnFloatingActionButtonClicked {
        void onFloatingActionButtonClicked(RecyclerView recyclerView);
    }

}
