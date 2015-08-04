package com.unal.larim.DataSource;

import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class ParticipantSource implements BaseColumns {
    public static final String table_name_participant = "registered";
    public static final String table_name_country = "country";
    public static final String column_name = "name";
    public static final String column_email = "email";
    public static final String column_institution = "institution";
    public static final String column_country_code = "code";
    public static final String column_country_name = "name";
    public static final String column_type = "type";
    public static final String column_names[] = new String[]{"a." +
            column_name, column_email, column_institution, column_country_code, column_type};
}
