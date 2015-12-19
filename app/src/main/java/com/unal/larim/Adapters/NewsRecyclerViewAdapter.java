package com.unal.larim.Adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unal.larim.Data.Notice;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private static final String TAG = NewsRecyclerViewAdapter.class.getSimpleName();
    public List<Notice> notices;
    private Context context;
    private Activity activity;

    private Notice remove;
    private int removedPosition;

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        NewsViewHolder pvh = new NewsViewHolder(v);
        return pvh;
    }

    @Override
    public void onViewRecycled(NewsViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int i) {
        holder.title.setText(notices.get(i).title);
        holder.content.setText(notices.get(i).content);
        holder.row.setBackgroundColor(ContextCompat.getColor(context,
                notices.get(i).checked ? R.color.darkblue : R.color.darkyellow));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public NewsRecyclerViewAdapter(ArrayList<Notice> notices, Activity activity) {
        this.notices = notices;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void delete(int position, View view) {
        if (position == -1) {
            return;
        }
        removedPosition = position;
        remove = notices.remove(position);
        notifyItemRemoved(position);
        ContentResolver contentResolver = context.getContentResolver();
        long retrieved = contentResolver.delete(
                NoticeContent.CONTENT_URI, NoticeContent._ID + "=?"
                , new String[]{remove.id});
        createSnackBar(view);
    }

    private void createSnackBar(View view) {
        Snackbar snackbar = Snackbar
                .make(view, context.getString(R.string.message_deleted), Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.setAction(context.getString(R.string.snackbar_undo),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUndo();
                    }
                });

        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.darkyellow));
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.darkblue));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }

    public void deleteUndo() {
        if (removedPosition == -1) {
            return;
        }
        notices.add(removedPosition, remove);
        notifyItemInserted(removedPosition);
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent._ID, remove.id);
        cv.put(NoticeContent.column_checked, remove.checked);
        cv.put(NoticeContent.column_content, remove.content);
        cv.put(NoticeContent.column_title, remove.title);
        cv.put(NoticeContent.column_url, remove.url);
        Uri retrieved = contentResolver.insert(NoticeContent.CONTENT_URI, cv);
        Util.log(TAG, retrieved.toString());
        removedPosition = -1;
        remove = null;
    }

    public void irWeb(int position) {
        Notice selected = notices.get(position);
        selected.checked = true;
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, 1);
        ContentResolver contentResolver = context.getContentResolver();
        long retrieved = contentResolver.update(
                NoticeContent.CONTENT_URI, cv, NoticeContent._ID + "=?"
                , new String[]{selected.id});
        notifyItemChanged(position);
        Util.log(TAG, retrieved + " " + selected.title + " con id " + retrieved);
        Util.irA(notices.get(position).url, activity);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView title;
        TextView content;
        LinearLayout row;
        ImageView imageDelete;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            row = (LinearLayout) itemView.findViewById(R.id.row);
            title = (TextView) itemView.findViewById(R.id.NoticeTitle);
            imageDelete = (ImageView) itemView.findViewById(R.id.image_delete);
            content = (TextView) itemView.findViewById(R.id.NoticeContent);
            cv.setOnClickListener(this);
            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(getAdapterPosition(), v);
                }
            });
        }

        @Override
        public void onClick(View v) {
            irWeb(getAdapterPosition());
        }

    }
}
