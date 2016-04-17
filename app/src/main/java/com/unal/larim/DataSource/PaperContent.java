package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.Data.Paper;

import java.util.ArrayList;
import java.util.List;

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

    public static Uri buildPaperParticipantUri(long participantID) {
        return CONTENT_URI.buildUpon().appendPath(
                "participant").appendPath(
                participantID + "").build();
    }

    public static Paper getPaper(long paperID, Context context) {
        if (paperID == -1) {
            return null;
        }
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(PaperContent.buildPaperUri(paperID), null, null, null, null);
        List<Paper> papers = getPapers(c);
        return papers.isEmpty() ? null : papers.get(0);
    }

    public static List<Paper> getPapers(Cursor c) {
        List<Paper> papers = new ArrayList<>();
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                String title = c.getString(c.getColumnIndex(column_title));
                String keywords = c.getString(c.getColumnIndex(column_keywords));
                String pdfURL = c.getString(c.getColumnIndex(column_pdf));
                int participantID = c.getInt(c.getColumnIndex(column_participant_id));
                papers.add(new Paper(title, keywords, pdfURL, participantID));
                c.moveToNext();
            }
        }
        c.close();
        return papers;
    }
}
