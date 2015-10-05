package com.unal.larim.Adapters;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
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

    private List<Notice> notices;
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
    public void onBindViewHolder(NewsViewHolder holder, int i) {
        holder.title.setText(notices.get(i).title);
        holder.content.setText(notices.get(i).content);
        holder.row.setBackgroundColor(context.getResources().getColor(
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

    public NewsRecyclerViewAdapter(ArrayList<Notice> noticias, Activity activity) {
        this.notices = noticias;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    public void delete(int position) {
        if (position == -1) {
            return;
        }
        Notice remove = notices.remove(position);
        notifyItemRemoved(position);
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, 1);
        long retrieved = contentResolver.delete(
                NoticeContent.CONTENT_URI, NoticeContent._ID + "=?"
                , new String[]{remove.id});
        Util.log("Item Eliminado", retrieved + " " + remove.title + " con id " + retrieved);
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
        Util.log("Item Cambiado", retrieved + " " + selected + " con id " + retrieved);
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
                    delete(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            irWeb(getAdapterPosition());
        }


    }
}
