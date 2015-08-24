package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class NoticeContent implements BaseColumns {
    public static final String table_name = "notice";
    public static final String column_title = "title";
    public static final String column_content = "content";
    public static final String column_checked = "checked";
    public static final String column_url = "url";
    public static final String column_names[] = new String[]{NoticeContent._ID, column_title,
            column_content, column_checked, column_url};
    public static final String NOTICE_PATH = "notice";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(NOTICE_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + NOTICE_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + NOTICE_PATH;

    public static Uri buildNoticeUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
