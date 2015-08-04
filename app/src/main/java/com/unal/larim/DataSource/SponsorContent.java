package com.unal.larim.DataSource;

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
}
