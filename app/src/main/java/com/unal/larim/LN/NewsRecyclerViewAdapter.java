package com.unal.larim.LN;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unal.larim.Data.Notice;
import com.unal.larim.DataSource.NoticeContent;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<Notice> noticias;
    private Context context;
    private Activity activity;

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
    public void onBindViewHolder(final NewsViewHolder holder, int i) {
        holder.title.setText(noticias.get(i).title);
        holder.content.setText(noticias.get(i).content);
        holder.title.setBackgroundColor(context.getResources().getColor(
                noticias.get(i).checked ? R.color.darkblue : R.color.darkyellow));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public NewsRecyclerViewAdapter(ArrayList<Notice> noticias, Activity activity) {
        this.noticias = noticias;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void delete(int position) {
        if (position == -1) {
            return;
        }
        Notice remove = noticias.remove(position);
        notifyItemRemoved(position);
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, true);
        long retrived = contentResolver.update(
                NoticeContent.CONTENT_URI, cv, NoticeContent._ID + "=?"
                , new String[]{remove.id});
    }

    public void irWeb(int position) {
        Notice remove = noticias.get(position);
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, true);
        ContentResolver contentResolver = context.getContentResolver();
        long retrived = contentResolver.update(
                NoticeContent.CONTENT_URI, cv, NoticeContent._ID + "=?"
                , new String[]{remove.id});
        notifyItemChanged(position);
        Util.log("Item Cambiado", retrived + " " + noticias.get(position).title + " con id " + retrived);
        Util.irA(noticias.get(position).url, activity);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView title;
        TextView content;

        NewsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            title = (TextView) itemView.findViewById(R.id.NoticeTitle);
            content = (TextView) itemView.findViewById(R.id.NoticeContent);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            irWeb(getPosition());
        }


    }
}
