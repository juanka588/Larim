package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class SponsorContent implements BaseColumns {
    public static final String table_name = "sponsor";
    public static final String column_name = "name";
    public static final String column_icon = "icon";
    public static final String column_website = "website";
    public static final String column_content = "content";
    public static final String column_type = "type";
    public static final String column_latitude = "latitude";
    public static final String column_longitude = "longitude";
    public static final String column_names[] = new String[]{column_name,
            column_icon, column_website, column_content};
    public static final String column_names2[] = new String[]{column_name,
            column_website, column_latitude, column_longitude};
    public static final String SPONSOR_PATH = "sponsor";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(SPONSOR_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + SPONSOR_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + SPONSOR_PATH;

    public static Uri buildSponsorUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
