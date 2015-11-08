package liufantech.com.yingxiongmao.custom.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import liufantech.com.yingxiongmao.R;
import liufantech.com.yingxiongmao.main.MainActivity;

/**
 * Created by HL0521 on 2015/10/31.
 */
public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    private boolean hasFocus;

    private boolean mEmpty;

    private ClearEditText.onClearEditTextChanged mOnClearEditTextChangedListener;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ico_delete);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                // getWidth():得到控件的宽度
                // getTotalPaddingRight():clear的图标左边缘至控件右边缘的距离
                // getPaddingRight():clear的图标右边缘至控件右边缘的距离
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() > 0) {
            setClearIconVisible(true);
            mEmpty = false;
        } else {
            setClearIconVisible(false);
            mEmpty = true;
        }
        if (mOnClearEditTextChangedListener != null) {
            mOnClearEditTextChangedListener.onClearEditTextChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], null, right, null);
    }

    public boolean isEmpty() {
        return mEmpty;
    }

    public void setOnClearEditTextChangedListener(ClearEditText.onClearEditTextChanged listener) {
        if (mOnClearEditTextChangedListener == null) {
            mOnClearEditTextChangedListener = listener;
        }
    }

    public interface onClearEditTextChanged {
        void onClearEditTextChanged();
    }
}
