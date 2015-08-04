package com.unal.larim.DataSource;

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
    public static final String column_names[] = new String[]{BaseColumns._ID, column_title,
            column_content, column_checked, column_url};
}
