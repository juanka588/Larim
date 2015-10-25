package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class CountryContent implements BaseColumns {
    public static final String table_name_country = "country";
    public static final String column_country_code = "code";
    public static final String column_country_name = "name_es";
    public static final String column_country_local_name = "local";
    public static final String column_names[] = new String[]{BaseColumns._ID, column_country_code,
            column_country_name, column_country_local_name};

    public static final String COUNTRY_PATH = "country";

    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(COUNTRY_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + COUNTRY_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + COUNTRY_PATH;


    public static Uri buildCountryUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildCountryUri(String code) {
        return CONTENT_URI.buildUpon().appendPath(code).build();
    }

}
