package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.Data.Sponsor;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

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
    public static final String SPONSOR_PATH = "sponsor";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(SPONSOR_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + SPONSOR_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + SPONSOR_PATH;

    public static Uri buildSponsorUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildSponsorUri(String filter) {
        return CONTENT_URI.buildUpon().appendPath(filter).build();
    }

    public static Uri buildMapsUri(String filter) {
        return CONTENT_URI.buildUpon().appendPath("info").appendPath(filter).build();
    }

    public static List<Sponsor> getSponsors(Context context, String filter) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(SponsorContent.buildSponsorUri(filter)
                , null, null, null, null);
        String[][] mat = Util.imprimirLista(cursor);
        List<Sponsor> sponsors = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            int icon = context.getResources().getIdentifier("drawable/" + mat[i][1], null,
                    context.getPackageName());
            if(icon==0){
                icon= R.drawable.larim;
            }
            sponsors.add(new Sponsor(mat[i][0], icon, mat[i][2], mat[i][3]));
        }
        cursor.close();
        return sponsors;
    }

}
