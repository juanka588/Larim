package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.Data.AuditoriumPlace;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanCamilo on 31/08/2016.
 */
public class VenueContent implements BaseColumns {
    public static final String TABLE_NAME = "venue";
    public static final String IMAGE_COLUMN = "image";
    public static final String TITLE_COLUMN = "title";
    public static final String GROUP_COLUMN = "type";
    public static final String ICON_COLUMN = "icon";

    public static final String[] COLUMN_NAMES = new String[]{IMAGE_COLUMN,
            TITLE_COLUMN, GROUP_COLUMN, ICON_COLUMN};

    public static final String VENUE_PATH = "venue";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(VENUE_PATH).build();

    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + VENUE_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + VENUE_PATH;

    public static Uri buildVenueUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildVenueUriGroup(int filter) {
        return CONTENT_URI.buildUpon().appendPath(GROUP_COLUMN).appendPath(String.valueOf(filter)).build();
    }

    public static List<AuditoriumPlace> getVenue(Context context, int group) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(buildVenueUriGroup(group)
                , null, null, null, null);
        String[][] mat = Util.imprimirLista(cursor);
        List<AuditoriumPlace> venues = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            int icon = context.getResources().getIdentifier("drawable/" + mat[i][3], null,
                    context.getPackageName());
            if (icon == 0) {
                icon = R.drawable.larim;
            }
            venues.add(new AuditoriumPlace(icon, mat[i][0], mat[i][1]));
        }
        cursor.close();
        return venues;
    }
}
