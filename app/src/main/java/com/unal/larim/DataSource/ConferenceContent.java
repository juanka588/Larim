package com.unal.larim.DataSource;

import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 04/08/2015.
 */
public class ConferenceContent implements BaseColumns {
    public static final String table_name_conference = "conference";
    public static final String table_name_chairman = "chairman";
    public static final String table_name_code = "code";
    public static final String table_names[] = new String[]{table_name_conference,
            table_name_chairman, table_name_code};
    public static final String column_title = "title";
    public static final String column_place = "place";
    public static final String column_hour = "hour";
    public static final String column_date = "date";
    public static final String column_paper_id = "paper";
    public static final String column_scheduled = "scheduled";
    public static final String column_code_id = "code";
    public static final String column_chairman_id = "chairman";
    public static final String column_chaiman_name = "name";
    public static final String column_code_initials = "initials";
    public static final String column_code_description = "description";
    public static final String column_names[] = new String[]{column_paper_id, column_title,
            column_place, column_hour, column_date, column_chaiman_name,
            column_scheduled, column_code_initials, column_code_description};
}
