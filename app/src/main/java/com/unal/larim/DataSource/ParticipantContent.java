package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.Data.Paper;
import com.unal.larim.Data.Participant;
import com.unal.larim.LN.Util;

import java.util.List;

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
    public static final String column_resume = "resume";
    public static final String column_image = "image";

    public static final String column_names[] = new String[]{
            column_name, column_email, column_institution, column_country_code, column_type,
            column_resume, column_image, BaseColumns._ID};

    public final static String TYPE_NORMAL = "-";
    public final static String TYPE_SCIENTIFIC_ORGANIZING_COMMITTEE = "SC";
    public final static String TYPE_LOCAL_ORGANIZING_COMMITTEE = "LC";
    public final static String TYPE_REVIEWS_TALK = "RT";
    public final static String TYPE_INVITED_TALK = "IT";
    public final static String TYPE_EXTERNAL_LOGISTICS_SUPPORT = "EC";
    public final static String TYPE_INTERNAL_LOGISTICS_SUPPORT = "A";
    public final static String TYPE_ASSISTANT = "AA";
    public final static String TYPE_STUDENT = "E";
    public final static String TYPE_PROFESSIONAL = "P";
    public final static String TYPE_LARIM_SUPPORT = "AL";
    public final static String TYPE_IAU_STAYING = "IAU-E";
    public final static String TYPE_IAU_TICKETS = "IAU-P";
    public final static String TYPE_IAU_INSCRIPTION = "IAU-I";
    public final static String TYPE_INVITED = "II";
    public final static String TYPE_PAYMENT_DATE_1 = "IP1";
    public final static String TYPE_PAYMENT_DATE_2 = "IP2";
    public final static String TYPE_PAYMENT_DATE_3 = "IP3";
    public final static String TYPE_DINNER_PAID = "CP";
    public final static String TYPE_DINNER_INVITED = "CI";

    public static final String PATH_PARTICIPANT = "participant";

    public static final String STRING_NORMAL = "Normal";
    public final static String STRING_SCIENTIFIC_ORGANIZING_COMMITTEE = "Scientific Organizing Committee";
    public final static String STRING_LOCAL_ORGANIZING_COMMITTEE = "Local Organizing Committee";
    public final static String STRING_REVIEWS_TALK = "Reviews Talks";
    public final static String STRING_INVITED_TALK = "Invited Talks";
    public final static String STRING_EXTERNAL_LOGISTICS_SUPPORT = "External Logistics Committee";
    public final static String STRING_INTERNAL_LOGISTICS_SUPPORT = "Internal Logistics Support";
    private static final String STRING_ASSISTANT = "Assistant";
    private static final String STRING_STUDENT = "Student";
    private static final String STRING_PROFESSIONAL = "Professional";
    private static final String STRING_LARIM_SUPPORT = "LARIM Support";
    private static final String STRING_IAU_STAYING = "Help Staying";
    private static final String STRING_IAU_INSCRIPTION = "Help Inscription";
    private static final String STRING_IAU_TICKETS = "Help tickets";
    private static final String STRING_INVITED = "Invited";
    private static final String STRING_PAYMENT_DATE_1 = "Before 1st august 2015";
    private static final String STRING_PAYMENT_DATE_2 = "Between august 2015-2016";
    private static final String STRING_PAYMENT_DATE_3 = "After 1st august 2016";
    private static final String STRING_DINNER_INVITED = "Dinner invited";
    private static final String STRING_DINNER_PAID = "Dinner paid";


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
            finalString.append("; ");
            finalString.append(switchString(arr[i].trim()));
        }
        return finalString.toString();
    }

    /***
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
        } else if (cad.equals(TYPE_ASSISTANT)) {
            return STRING_ASSISTANT;
        } else if (cad.equals(TYPE_STUDENT)) {
            return STRING_STUDENT;
        } else if (cad.equals(TYPE_PROFESSIONAL)) {
            return STRING_PROFESSIONAL;
        } else if (cad.equals(TYPE_LARIM_SUPPORT)) {
            return STRING_LARIM_SUPPORT;
        } else if (cad.equals(TYPE_IAU_STAYING)) {
            return STRING_IAU_STAYING;
        } else if (cad.equals(TYPE_IAU_INSCRIPTION)) {
            return STRING_IAU_INSCRIPTION;
        } else if (cad.equals(TYPE_IAU_TICKETS)) {
            return STRING_IAU_TICKETS;
        } else if (cad.equals(TYPE_INVITED)) {
            return STRING_INVITED;
        } else if (cad.equals(TYPE_PAYMENT_DATE_1)) {
            return STRING_PAYMENT_DATE_1;
        } else if (cad.equals(TYPE_PAYMENT_DATE_2)) {
            return STRING_PAYMENT_DATE_2;
        } else if (cad.equals(TYPE_PAYMENT_DATE_3)) {
            return STRING_PAYMENT_DATE_3;
        } else if (cad.equals(TYPE_DINNER_INVITED)) {
            return STRING_DINNER_INVITED;
        } else if (cad.equals(TYPE_DINNER_PAID)) {
            return STRING_DINNER_PAID;
        } else if (cad.equals(TYPE_INTERNAL_LOGISTICS_SUPPORT)) {
            return STRING_INTERNAL_LOGISTICS_SUPPORT;
        } else if (cad.equals("null") || cad.equals("")) {
            return STRING_NORMAL;
        }
        return "NOT_VALID";
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

    public static Participant getAuthorFromID(long id, Context context) {
        Cursor cursor = context.getContentResolver().query(ParticipantContent.buildParticipantUri(id),
                null, null, null, null);
        String mat[][] = Util.imprimirLista(cursor);
        Participant participant = null;
        for (int i = 0; i < mat.length; i++) {
            participant = new Participant(mat[i][0], mat[i][1], mat[i][2], mat[i][3],
                    mat[i][4], mat[i][5], mat[i][6], Long.parseLong(mat[i][7]));
        }
        cursor.close();
        return participant;
    }

    public static Paper getPaper(long participantID, Context context) {
        Cursor cursor = context.getContentResolver().query(PaperContent.buildPaperParticipantUri(participantID),
                null, null, null, null);
        List<Paper> papers = PaperContent.getPapers(cursor);
        cursor.close();
        return papers.isEmpty() ? null : papers.get(0);
    }
}
