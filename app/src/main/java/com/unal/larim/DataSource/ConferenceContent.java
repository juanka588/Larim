package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 04/08/2015.
 */
public class ConferenceContent implements BaseColumns {
    public static final String table_name_conference = "conference";
    public static final String table_name_chairman = "registered";
    public static final String table_name_code = "code";
    public static final String table_names[] = new String[]{table_name_conference,
            table_name_chairman, table_name_code};
    public static final String column_title = "title";
    public static final String column_place = "place";
    public static final String column_hour_start = "start";
    public static final String column_hour = "hour";
    public static final String column_hour_end = "end";
    public static final String column_date = "date";
    public static final String column_paper_id = "paper";
    public static final String column_scheduled = "scheduled";
    public static final String column_code_id = "code";
    public static final String column_chairman = "chairman";
    public static final String column_chairman_id = table_name_chairman + BaseColumns._ID;
    public static final String column_code_initials = "initials";
    public static final String column_code_description = "description";
    public static final String column_names[] = new String[]{column_paper_id, column_title,
            column_place, column_hour_start, column_hour_end, column_date, column_chairman,
            column_scheduled, column_code_initials, column_code_description, column_hour};
    public static final String CONFERENCE_PATH = "conference";

    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(CONFERENCE_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + CONFERENCE_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + CONFERENCE_PATH;

    public static Uri buildConferenceUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildConferenceDateUri(long date) {
        return ContentUris.withAppendedId(CONTENT_URI, date);
    }
    public static Uri buildConferenceHourDateUri(String hour, long date) {
        return CONTENT_URI.buildUpon().appendPath(hour).appendPath(Long.toString(date)).build();
    }
}
