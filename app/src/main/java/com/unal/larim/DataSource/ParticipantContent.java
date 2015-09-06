package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.LN.Util;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class ParticipantContent implements BaseColumns {
    public static final String table_name_participant = "registered";
    public static final String table_name_country = "country";
    public static final String column_name = "name";
    public static final String column_email = "email";
    public static final String column_institution = "institution";
    public static final String column_country_code = "code";
    public static final String column_country_name = "name";
    public static final String column_type = "type";
    public static final String column_names[] = new String[]{"a." +
            column_name, column_email, column_institution, column_country_code, column_type,
            "a." + BaseColumns._ID + " as _id"};

    public final static String TYPE_NORMAL = "-";
    public final static String TYPE_SCIENTIFIC_ORGANIZING_COMMITTEE = "SC";
    public final static String TYPE_LOCAL_ORGANIZING_COMMITTEE = "LC";
    public final static String TYPE_REVIEWS_TALK = "RT";
    public final static String TYPE_INVITED_TALK = "IT";
    public final static String TYPE_EXTERNAL_LOGISTICS_SUPPORT = "EC";
    public final static String TYPE_INTERNAL_LOGISTICS_SUPPORT = "A";
    public static final String PATH_PARTICIPANT = "participant";

    public static final String STRING_NORMAL = "Normal";
    public final static String STRING_SCIENTIFIC_ORGANIZING_COMMITTEE = "Scientific Organizing Committee";
    public final static String STRING_LOCAL_ORGANIZING_COMMITTEE = "Local Organizing Committee";
    public final static String STRING_REVIEWS_TALK = "Reviews Talks";
    public final static String STRING_INVITED_TALK = "Invited Talks";
    public final static String STRING_EXTERNAL_LOGISTICS_SUPPORT = "External Logistics Committee";
    public final static String STRING_INTERNAL_LOGISTICS_SUPPORT = "Internal Logistics Support";

    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARTICIPANT).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + PATH_PARTICIPANT;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + PATH_PARTICIPANT;


    public static Uri buildParticipantUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildParticipantUri(String name) {
        return CONTENT_URI.buildUpon().appendPath(
                column_name).appendPath(name).build();
    }

    public static Uri buildParticipantUri(String column, String filter) {
        return CONTENT_URI.buildUpon().appendPath(
                column).appendPath(filter).build();
    }

    public static String getTypeString(String cad) {
        if(cad.contains(TYPE_SCIENTIFIC_ORGANIZING_COMMITTEE)){
            return STRING_SCIENTIFIC_ORGANIZING_COMMITTEE;
        }
        if(cad.equals(TYPE_LOCAL_ORGANIZING_COMMITTEE)){
            return STRING_LOCAL_ORGANIZING_COMMITTEE;
        }
        if(cad.equals(TYPE_REVIEWS_TALK)){
            return STRING_REVIEWS_TALK;
        }
        if(cad.equals(TYPE_INVITED_TALK)){
            return STRING_INVITED_TALK;
        }
        if(cad.equals(TYPE_EXTERNAL_LOGISTICS_SUPPORT)){
            return STRING_EXTERNAL_LOGISTICS_SUPPORT;
        }
        if(cad.equals(TYPE_INTERNAL_LOGISTICS_SUPPORT)){
            return STRING_INTERNAL_LOGISTICS_SUPPORT;
        }
        if(cad.equals("null")){
            return STRING_NORMAL;
        }
        return "";
    }
}
