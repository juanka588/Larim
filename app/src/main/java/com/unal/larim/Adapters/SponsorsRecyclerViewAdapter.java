package com.unal.larim.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unal.larim.Data.Sponsor;
import com.unal.larim.GUI.InformationActivity;
import com.unal.larim.R;

import java.util.List;

/**
 * Created by JuanCamilo on 29/06/2015.
 */
public class SponsorsRecyclerViewAdapter extends RecyclerView.Adapter<SponsorsRecyclerViewAdapter.SponsorViewHolder> {

    private List<Sponsor> sponsors;
    private Context context;
    private Activity act;

    @Override
    public SponsorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewsponsor, parent, false);
        SponsorViewHolder pvh = new SponsorViewHolder(v);
        return pvh;
    }

    @Override
    public void onViewRecycled(SponsorViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final SponsorViewHolder holder, int i) {
        Sponsor s = sponsors.get(i);
        holder.name.setText(s.name);
        holder.icon.setImageResource(s.icon);
        holder.icon.setEnabled(false);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return sponsors.size();
    }

    public SponsorsRecyclerViewAdapter(List<Sponsor> sponsors, Activity act) {
        this.sponsors = sponsors;
        this.context = act.getApplicationContext();
        this.act = act;
    }

    public void openWeb(int position) {
        Intent info = new Intent(act, InformationActivity.class);
        info.putExtra(act.getString(R.string.ARG_TAG_INFORMATION), sponsors.get(position));
        act.startActivity(info);
    }

    public class SponsorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView name;
        ImageView icon;

        SponsorViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_sponsor);
            name = (TextView) itemView.findViewById(R.id.sponsorDescription);
            icon = (ImageView) itemView.findViewById(R.id.sponsorImage);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            openWeb(getAdapterPosition());
        }
    }
}
