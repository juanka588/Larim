package com.unal.larim.DataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.unal.larim.Data.Notice;
import com.unal.larim.LN.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanCamilo on 03/08/2015.
 */
public class NoticeContent implements BaseColumns {
    public static final String table_name = "notice";
    public static final String column_title = "title";
    public static final String column_content = "content";
    public static final String column_checked = "checked";
    public static final String column_url = "url";
    public static final String column_names[] = new String[]{NoticeContent._ID, column_title,
            column_content, column_checked, column_url};
    public static final String NOTICE_PATH = "notice";
    public static final Uri CONTENT_URI =
            LARIMContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(NOTICE_PATH).build();
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + NOTICE_PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    LARIMContentProvider.CONTENT_AUTHORITY + "/" + NOTICE_PATH;

    public static Uri buildNoticeUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static int getUnreadNews(Context context) {
        List<Notice> news = getNews(context);
        int count = 0;
        for (Notice n : news) {
            if (!n.isChecked()) {
                count++;
            }
        }
        return count;
    }

    public static List<Notice> getNews(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(NoticeContent.CONTENT_URI, null, null, null, null);
        String[][] mat = Util.imprimirLista(c);
        List<Notice> notices = new ArrayList<>();
        for (int i = 0; i < mat.length; i++) {
            notices.add(new Notice(mat[i][0], mat[i][1], mat[i][2], mat[i][3], mat[i][4]));
        }
        c.close();
        Util.log("Noticias size", notices.size() + "");
        return notices;
    }

}
