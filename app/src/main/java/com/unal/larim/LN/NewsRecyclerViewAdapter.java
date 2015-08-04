package com.unal.larim.LN;

import android.app.Activity;
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
import android.widget.Toast;

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
        LinnaeusDatabase lb = new LinnaeusDatabase(context);
        SQLiteDatabase db = lb.dataBase;
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, "1");
        long retrived = db.update(NoticeContent.table_name, cv, BaseColumns._ID + " = " + remove.id, null);
    }

    public void irWeb(int position) {
        Notice remove = noticias.get(position);
        LinnaeusDatabase lb = new LinnaeusDatabase(context);
        SQLiteDatabase db = lb.dataBase;
        ContentValues cv = new ContentValues();
        cv.put(NoticeContent.column_checked, "1");
        long retrived = db.update(NoticeContent.table_name, cv, BaseColumns._ID + " = " + remove.id, null);
        notifyItemChanged(position);
        Util.log("Item Cambiado", noticias.get(position).title + " con id " + retrived);
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
