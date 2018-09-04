package com.leelu.library.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : leelu
 * CreateTime : 2018/8/7 20:38
 * Description :
 */
public class MemoryConstants {

    /**
     * byte
     */
    public static final int BYTE = 1;

    /**
     * byte 与 KB 之间的转换
     */
    public static final int KB = 1024;

    /**
     * byte与 MB 之间的转换
     */
    public static final int MB = 1048576;

    /**
     * byte 与 GB 之间的转换
     */
    public static final int GB = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

}