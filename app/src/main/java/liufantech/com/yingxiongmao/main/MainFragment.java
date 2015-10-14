package liufantech.com.yingxiongmao.main;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.logging.Handler;

import liufantech.com.yingxiongmao.R;


public class MainFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATEGORY = "category";

    // TODO: Rename and change types of parameters
    private String mCategory;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerViewScrollDetector mRecyclerViewScrollDetector;
    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;
    private FloatingActionButton mFloatingActionButton;

    private android.os.Handler mHandler;
    private Context mContext;

    private MainFragment.OnFloatingActionButtonClicked mOnFloatingActionButtonListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String category) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
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

        switch (mCategory) {
            case MainConstant.CATEGORY_HOMEPAGE:
                view = inflater.inflate(R.layout.fragment_homepage, container, false);
                break;
            case MainConstant.CATEGORY_NBA:
                view = inflater.inflate(R.layout.fragment_nba, container, false);
                break;
            case MainConstant.CATEGORY_FOOTBALL:
                view = inflater.inflate(R.layout.fragment_football, container, false);
                break;
            case MainConstant.CATEGORY_FITNESS:
                view = inflater.inflate(R.layout.fragment_fitness, container, false);
                break;
            case MainConstant.CATEGORY_TENNIS:
                view = inflater.inflate(R.layout.fragment_tennis, container, false);
                break;
            case MainConstant.CATEGORY_BEAUTY:
                view = inflater.inflate(R.layout.fragment_beauty, container, false);
                break;
            default:
                view = null;
                return view;
        }

        initRecyclerView(view);

        return view;
    }

    public void initRecyclerView(View view) {
        if (view != null) {

            mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerViewLayoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);

            mRecyclerViewAdapter = new MainRecyclerViewAdapter(mCategory);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);

            mFloatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
//            mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "FloatingActionBar", Toast.LENGTH_SHORT).show();
////                    mRecyclerViewLayoutManager.scrollToPosition(0);
//                    mOnFloatingActionButtonListener.onFloatingActionButtonClicked(mRecyclerView);
//
//                }
//            });

            mRecyclerViewScrollDetector = new MyRecyclerViewScrollDetector(mFloatingActionButton);
            mRecyclerViewScrollDetector.setmScrollThreshold(15);

            mRecyclerView.addOnScrollListener(mRecyclerViewScrollDetector);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public RecyclerView.LayoutManager getmRecyclerViewLayoutManager() {
        return mRecyclerViewLayoutManager;
    }

    public interface OnFloatingActionButtonClicked {
        void onFloatingActionButtonClicked(RecyclerView recyclerView);
    }
}
