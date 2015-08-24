package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class PaperContent implements BaseColumns {
    public static final String table_name = "paper";
    public static final String column_title = "title";
    public static final String column_keywords = "keywords";
    public static final String column_pdf = "pdf";
    public static final String column_participant_id = "registered";
    public static final String column_names[] = new String[]{column_title,
            column_keywords, column_pdf, column_participant_id};
    public static final String PAPER_PATH = "paper";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(PAPER_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + PAPER_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + PAPER_PATH;

    public static Uri buildPaperUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
