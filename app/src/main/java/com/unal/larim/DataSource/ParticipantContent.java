package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class ParticipantContent implements BaseColumns {
    public static final String table_name_participant = "registered";
    public static final String column_name = "name";
    public static final String column_email = "email";
    public static final String column_institution = "institution";
    public static final String column_country_code = "country";
    public static final String column_type = "type";
    public static final String column_names[] = new String[]{
            column_name, column_email, column_institution, column_country_code, column_type,
            BaseColumns._ID};

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

    public static String getTypeString(String type) {
        StringBuilder finalString = new StringBuilder();
        String arr[] = type.split(",");
        finalString.append(switchString(arr[0]));
        for (int i = 1; i < arr.length; i++) {
            finalString.append("\n");
            finalString.append(switchString(arr[i]));
        }
        return finalString.toString();
    }

    /***
     *
     * @param cad
     * @return
     */
    public static String switchString(String cad) {
        if (cad.equals(TYPE_SCIENTIFIC_ORGANIZING_COMMITTEE)) {
            return STRING_SCIENTIFIC_ORGANIZING_COMMITTEE;
        } else if (cad.equals(TYPE_LOCAL_ORGANIZING_COMMITTEE)) {
            return STRING_LOCAL_ORGANIZING_COMMITTEE;
        } else if (cad.equals(TYPE_REVIEWS_TALK)) {
            return STRING_REVIEWS_TALK;
        } else if (cad.equals(TYPE_INVITED_TALK)) {
            return STRING_INVITED_TALK;
        } else if (cad.equals(TYPE_EXTERNAL_LOGISTICS_SUPPORT)) {
            return STRING_EXTERNAL_LOGISTICS_SUPPORT;
        } else if (cad.equals(TYPE_INTERNAL_LOGISTICS_SUPPORT)) {
            return STRING_INTERNAL_LOGISTICS_SUPPORT;
        } else if (cad.equals("null")) {
            return STRING_NORMAL;
        }
        return "";
    }

    public static String getFilterString(String cad) {
        if (cad == null) {
            return "";
        }
        if (cad.equals(STRING_SCIENTIFIC_ORGANIZING_COMMITTEE)) {
            return TYPE_SCIENTIFIC_ORGANIZING_COMMITTEE;
        }
        if (cad.equals(STRING_LOCAL_ORGANIZING_COMMITTEE)) {
            return TYPE_LOCAL_ORGANIZING_COMMITTEE;
        }
        if (cad.equals(STRING_REVIEWS_TALK)) {
            return TYPE_REVIEWS_TALK;
        }
        if (cad.equals(STRING_INVITED_TALK)) {
            return TYPE_INVITED_TALK;
        }
        if (cad.equals(STRING_EXTERNAL_LOGISTICS_SUPPORT)) {
            return TYPE_EXTERNAL_LOGISTICS_SUPPORT;
        }
        if (cad.equals(STRING_INTERNAL_LOGISTICS_SUPPORT)) {
            return TYPE_INTERNAL_LOGISTICS_SUPPORT;
        }
        if (cad.equals(STRING_NORMAL)) {
            return TYPE_NORMAL;
        }
        return "";
    }
}
