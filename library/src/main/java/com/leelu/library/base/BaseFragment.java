package com.leelu.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author : leelu
 * CreateTime : 2018/8/10 14:46
 * Description : BaseFragment
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    /**
     * Log打印中的Tag
     */
    protected static String TAG;
    /**
     * 上下文环境
     */
    protected Context mContext;
    /**
     * FragmentAttach的Activity
     */
    protected Activity mActivity;
    /**
     * ButterKnife的绑定
     */
    protected Unbinder mUnBinder;
    protected View mContentView;
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        mContext = context;
        mActivity = getActivity();
        mCompositeDisposable = new CompositeDisposable();
        // 初始化参数
        initArgs(getArguments());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                FragmentTransaction ft = fm.beginTransaction();
                if (isSupportHidden) {
                    ft.hide(this);
                } else {
                    ft.show(this);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setBaseView(inflater,bindLayout());
        return mContentView;
    }

    protected void setBaseView(@NonNull LayoutInflater inflater, @LayoutRes int layId) {
        if (layId != 0) {
            mContentView = inflater.inflate(layId, null);
        }else {
            throw new RuntimeException("布局文件id初始化错误");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        initView(savedInstanceState, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        doBusiness();
    }

    @Override
    public void onDestroyView() {
        // 把当前Root从其父控件中移除
        if (mContentView != null) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 初始化相关参数
     *
     * @param args 参数Bundle
     */
    protected void initArgs(@Nullable final Bundle args) {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * @return 去除抖动
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (!isFastClick()) onViewClicked(view);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (mContentView == null) {
            throw new NullPointerException("ContentView is null.");
        }
        return mContentView.findViewById(id);
    }
}
