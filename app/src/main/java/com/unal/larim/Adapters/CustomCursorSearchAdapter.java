package com.unal.larim.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by JuanCamilo on 06/09/2015.
 */
public class CustomCursorSearchAdapter extends SimpleCursorAdapter {
    String mOriginalFrom[];

    public CustomCursorSearchAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mOriginalFrom = from;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        for (int i = 0; i < mTo.length; i++) {
            TextView tx = (TextView) view.findViewById(mTo[i]);
            tx.setText(cursor.getString(cursor.getColumnIndex(mOriginalFrom[i])));
        }
        super.bindView(view, context, cursor);
    }
}
