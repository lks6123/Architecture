package com.leelu.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author : leelu
 * CreateTime : 2018/8/10 14:06
 * Description : BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected static String TAG;
    protected Context mContext;
    protected View mContentView;
    protected Unbinder mUnbinder;
    protected CompositeDisposable mCompositeDisposable;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面未初始化之前初始化窗口
        initWidows();
        mContext = this;
        TAG = this.getClass().getSimpleName();
        mCompositeDisposable = new CompositeDisposable();
        setBaseView(bindLayout());
        //绑定布局文件
        mUnbinder = ButterKnife.bind(this);
        //初始化View
        initView(savedInstanceState, mContentView);
        //初始化Data数据
        initData();
        //执行业务逻辑
        doBusiness();
    }

    /**
     * 初始化BaseView
     *
     * @param layId 要绑定的LayoutId
     */
    protected void setBaseView(int layId) {
        if (initArgs(getIntent().getExtras())) {
            // 得到界面Id并设置到Activity界面中
            if (layId != 0) {
                mContentView = LayoutInflater.from(this).inflate(layId, null);
                setContentView(mContentView);
            } else {
                throw new RuntimeException("布局文件id初始化错误");
            }
        } else {
            finish();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        // 如果有直接Return
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }

    /**
     * 初始化相关参数
     *
     * @param args Intent中传递的参数Bundle
     * @return 如果参数正确返回 true，错误返回False
     */
    protected boolean initArgs(@Nullable final Bundle args) {
        return true;
    }

    /**
     * 初始化窗口
     */
    public void initWidows() {
    }

    /**
     * 重载当前页面 Activity
     */
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
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
    public void onClick(final View view) {
        if (!isFastClick()) onViewClicked(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }



}
