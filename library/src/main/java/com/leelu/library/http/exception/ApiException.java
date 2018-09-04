package com.leelu.library.http.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.MalformedJsonException;


import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @author : leelu
 * CreateTime : 2018/8/3 11:30
 * Description : 统一处理Api异常
 */
public class ApiException extends Exception {

    private int code;
    private String message;
    private String displayMessage;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String msg) {
        this.displayMessage = msg + "(code:" + code + ")";
    }

    /**
     * @param e 网络请求中抛出的Throwable
     * @return 统一的异常处理
     */
    public static ApiException handleException(Throwable e) {
        ApiException throwable;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            throwable = new ApiException(e, Error.HTTP_ERROR);
            throwable.message = "网络错误 " + httpException.code() + " " + httpException.message();
            return throwable;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            throwable = new ApiException(resultException, resultException.getCode());
            throwable.message = resultException.getMessage();
            return throwable;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof MalformedJsonException
                || e instanceof JsonSerializer
                || e instanceof NotSerializableException
                || e instanceof ParseException) {
            throwable = new ApiException(e, Error.PARSE_ERROR);
            throwable.message = "解析错误";
            return throwable;
        } else if (e instanceof ClassCastException) {
            throwable = new ApiException(e, Error.CAST_ERROR);
            throwable.message = "类型转换错误";
            return throwable;
        } else if (e instanceof ConnectException) {
            throwable = new ApiException(e, Error.NETWORK_ERROR);
            throwable.message = "连接失败";
            return throwable;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            throwable = new ApiException(e, Error.SSL_ERROR);
            throwable.message = "证书验证失败";
            return throwable;
        } else if (e instanceof ConnectTimeoutException) {
            throwable = new ApiException(e, Error.TIMEOUT_ERROR);
            throwable.message = "连接超时";
            return throwable;
        } else if (e instanceof java.net.SocketTimeoutException) {
            throwable = new ApiException(e, Error.TIMEOUT_ERROR);
            throwable.message = "连接超时";
            return throwable;
        } else if (e instanceof UnknownHostException) {
            throwable = new ApiException(e, Error.UNKNOWN_HOST_ERROR);
            throwable.message = "无法解析该域名";
            return throwable;
        } else if (e instanceof NullPointerException) {
            throwable = new ApiException(e, Error.NULL_POINTER_EXCEPTION);
            throwable.message = "NullPointerException";
            return throwable;
        } else {
            throwable = new ApiException(e, Error.UNKNOWN);
            throwable.message = "未知错误";
            return throwable;
        }
    }


    /**
     * 约定异常
     */
    public class Error {

        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1004;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1005;

        /**
         * 调用错误
         */
        public static final int INVOKE_ERROR = 1006;
        /**
         * 类转换错误
         */
        public static final int CAST_ERROR = 1007;
        /**
         * 请求取消
         */
        public static final int REQUEST_CANCEL = 1008;
        /**
         * 未知主机错误
         */
        public static final int UNKNOWN_HOST_ERROR = 1009;

        /**
         * 空指针错误
         */
        public static final int NULL_POINTER_EXCEPTION = 1010;
    }
}
