package com.leelu.library.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author : leelu
 * CreateTime : 2018/9/3 23:11
 * Description :
 */
public interface IBaseView extends View.OnClickListener {

    /**
     * View 初始化之后，进行一些数据的初始化
     *
     * 初始化数据
     */
    void initData();

    /**
     * 得到当前界面的 LayoutId， 子类必须实现，绑定布局
     *
     * @return ContentLayoutId 返回布局文件的 Layout Id
     */
    @LayoutRes
    int bindLayout();

    /**
     * 初始化 View 控件
     *
     * @param savedInstanceState Activity 被销毁创建
     * @param contentView        内容布局 View
     */
    void initView(final Bundle savedInstanceState, final View contentView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    void onViewClicked(final View view);
}
