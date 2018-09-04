package com.leelu.library.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : leelu
 * CreateTime : 2018/8/7 20:32
 * Description :
 */
public class TimeConstants {
    /**
     * 毫秒
     */
    public static final int MSEC = 1;
    /**
     * 秒与毫秒的换算
     */
    public static final int SEC  = 1000;
    /**
     * 分钟与毫秒的换算
     */
    public static final int MIN  = 60000;
    /**
     * 小时与毫秒的换算
     */
    public static final int HOUR = 3600000;
    /**
     * 天与毫秒的换算
     */
    public static final int DAY  = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
