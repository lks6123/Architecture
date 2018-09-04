package com.leelu.library.http.subsciber;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.leelu.library.http.exception.ApiException;
import com.leelu.library.listener.ProgressCancelListener;
import com.leelu.library.utils.ToastUtils;

/**
 * @author : leelu
 * CreateTime : 2018/8/7 14:38
 * Description :
 */
public abstract class NetProgressObserver extends BaseObserver implements ProgressCancelListener {
    private IProgressDialog mProgressDialog;
    private boolean mShowProgress = true;
    private Dialog mDialog;

    /**
     * 默认不显示弹出框，不可以取消
     *
     * @param context 上下文
     */
    public NetProgressObserver(Context context) {
        super(context);
        init(false);
    }

    /**
     * 自定义加载进度框
     *
     * @param context        上下文
     * @param progressDialog 自定义对话框
     */
    public NetProgressObserver(Context context, IProgressDialog progressDialog) {
        super(context);
        this.mProgressDialog = progressDialog;
        init(false);
    }

    /**
     * 自定义加载进度框
     *
     * @param context        上下文
     * @param progressDialog 自定义对话框
     * @param isShowProgress 是否显示dialog
     * @param cacelable      dialog是否可以取消
     */
    public NetProgressObserver(Context context, IProgressDialog progressDialog, boolean isShowProgress, boolean cacelable) {
        super(context);
        mProgressDialog = progressDialog;
        mShowProgress = isShowProgress;
        init(cacelable);
    }


    /**
     * @param cancelable 对话框是否可以取消
     */
    private void init(boolean cancelable) {
        if (mProgressDialog == null) return;
        mDialog = mProgressDialog.getDialog();
        if (this.mDialog == null) return;
        this.mDialog.setCancelable(cancelable);
        if (cancelable) {
            this.mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    onCancelProgress();
                }
            });
        }
    }


    /**
     * 展示进度框
     */
    private void showProgress() {
        if (!mShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (!mShowProgress) {
            return;
        }
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void onStart() {
        showProgress();
    }

    @Override
    public void onComplete() {
        dismissProgress();
    }

    @Override
    public void onError(ApiException e) {
        dismissProgress();
        int errCode = e.getCode();
        if (errCode == ApiException.Error.TIMEOUT_ERROR) {
            ToastUtils.showShort( "连接超时，请检查您的网络状态");
        } else if (errCode == ApiException.Error.NETWORK_ERROR) {
            ToastUtils.showShort("网络连接失败");
        }
    }

    @Override
    public void onCancelProgress() {
        if (!isDisposed()) {
            dispose();
        }
    }
}
