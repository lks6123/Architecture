package com.leelu.library.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.leelu.library.App;
import com.leelu.library.R;
import com.leelu.library.utils.BarUtils;

/**
 * @author : leelu
 * CreateTime : 2018/9/5 01:10
 * Description :
 */
public abstract class BaseBackActivity extends BaseActivity {
    protected CoordinatorLayout rootLayout;
    protected Toolbar mToolbar;
    protected AppBarLayout abl;
    protected FrameLayout flActivityContainer;

    @Override
    protected void setBaseView(int layId) {
        if (initArgs(getIntent().getExtras())) {
            mContentView = LayoutInflater.from(this).inflate(R.layout.activity_back, null);
            setContentView(mContentView);
            rootLayout = findViewById(R.id.root_layout);
            abl = findViewById(R.id.abl);
            mToolbar = findViewById(R.id.toolbar);
            flActivityContainer = findViewById(R.id.activity_container);
            if (layId > 0) {
                flActivityContainer.addView(LayoutInflater.from(this).inflate(layId, flActivityContainer, false));
            }
            setSupportActionBar(mToolbar);
            getToolBar().setDisplayHomeAsUpEnabled(true);

            BarUtils.setStatusBarColor(this, ContextCompat.getColor(App.getInstance(), R.color.colorPrimary), 0);
            BarUtils.addMarginTopEqualStatusBarHeight(rootLayout);
        }else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }
}
