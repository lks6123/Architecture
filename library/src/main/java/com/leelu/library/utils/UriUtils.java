package com.leelu.library.utils;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author : leelu
 * CreateTime : 2018/8/30 23:45
 * Description : Uri 相关的 Utils
 * <p>
 * file2Uri: file 转 uri
 * uri2File: uri 转 file
 */
public final class UriUtils {

    private UriUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * File to uri.
     *
     * @param file The file.
     * @return uri
     */
    public static Uri file2Uri(final File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = Utils.getApp().getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(Utils.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * Uri to file.
     *
     * @param uri        The uri.
     * @param columnName The name of the target column.
     *                   <p>e.g. {@link MediaStore.Images.Media#DATA}</p>
     * @return file
     */
    public static File uri2File(final Uri uri, final String columnName) {
        if (uri == null) return null;
        CursorLoader cl = new CursorLoader(Utils.getApp());
        cl.setUri(uri);
        cl.setProjection(new String[]{columnName});
        Cursor cursor = cl.loadInBackground();
        int columnIndex = cursor.getColumnIndexOrThrow(columnName);
        cursor.moveToFirst();
        return new File(cursor.getString(columnIndex));
    }
}
