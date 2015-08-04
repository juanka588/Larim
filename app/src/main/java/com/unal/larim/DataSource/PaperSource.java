package com.unal.larim.DataSource;

import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class PaperSource implements BaseColumns {
    public static final String table_name = "paper";
    public static final String column_title = "title";
    public static final String column_keywords = "keywords";
    public static final String column_pdf = "pdf";
    public static final String column_participant_id = "registered";
    public static final String column_names[] = new String[]{column_title,
            column_keywords, column_pdf, column_participant_id};
}
