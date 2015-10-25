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

import com.unal.larim.Data.AuditoriumPlace;
import com.unal.larim.GUI.AuditoriumDetailActivity;
import com.unal.larim.GUI.AuditoriumDetailActivityFragment;
import com.unal.larim.LN.Util;
import com.unal.larim.R;

import java.util.List;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class AuditoriumRecyclerViewAdapter extends RecyclerView.Adapter<AuditoriumRecyclerViewAdapter.PlacesViewHolder> {

    private List<AuditoriumPlace> mPlaces;
    private Context context;
    private Activity mActivity;


    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewplace, parent, false);
        PlacesViewHolder pvh = new PlacesViewHolder(v);
        return pvh;
    }

    @Override
    public void onViewRecycled(PlacesViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(PlacesViewHolder holder, int i) {
        AuditoriumPlace p = mPlaces.get(i);
        holder.icon.setImageResource(p.getIcon());
        holder.title.setText(p.getTitle());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public AuditoriumRecyclerViewAdapter(List<AuditoriumPlace> places, Activity activity) {
        this.mPlaces = places;
        this.context = activity.getApplicationContext();
        this.mActivity = activity;
    }

    public void showImage(int position) {
        /*TODO: center and display image */
        String style="<style type='text/css'>" +
                "img{" +
                "display: block;" +
                "margin-left: auto;" +
                "margin-right: auto;" +
                "border:none;" +
                "}" +
                "</style>";
        String imagePath = "file:///android_res/drawable/" + mPlaces.get(position).getImage();
        String data = "<html><body>" + "<img src=\""+imagePath+"\"/></body></html>";
        Intent intent=new Intent(mActivity, AuditoriumDetailActivity.class);
        AuditoriumDetailActivityFragment.SOURCE=imagePath;
        mActivity.startActivity(intent);
        Util.irA(imagePath,mActivity,false);
    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        ImageView icon;
        TextView title;

        PlacesViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_place);
            icon = (ImageView) itemView.findViewById(R.id.placeIcon);
            title = (TextView) itemView.findViewById(R.id.placeTitle);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showImage(getAdapterPosition());
        }

    }
}
