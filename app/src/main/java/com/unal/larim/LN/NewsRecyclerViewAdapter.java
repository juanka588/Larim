package com.unal.larim.LN;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unal.larim.NewsActivity;
import com.unal.larim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    private List<Notice> noticias;
    private Context context;

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
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public NewsRecyclerViewAdapter(ArrayList<Notice> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    public void delete(int position) {
        if (position == -1) {
            return;
        }
        Notice remove = noticias.remove(position);
        notifyItemRemoved(position);
        LinnaeusDatabase lb = new LinnaeusDatabase(context);
        SQLiteDatabase db = context.openOrCreateDatabase(LinnaeusDatabase.DATABASE_NAME,
                context.MODE_WORLD_READABLE, null);
        ContentValues cv = new ContentValues();
        cv.put(NewsActivity.column_names[3], "1");
        long retrived = db.update(NewsActivity.table_name, cv, " _id = " + remove.id, null);
        Util.log("Eliminado", "titulo " + remove.title + " total: " + retrived);
        Toast.makeText(context, "Eliminado: titulo " + remove.title + " total: " + retrived, Toast.LENGTH_SHORT).show();
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
            delete(getPosition());
        }


    }
}
