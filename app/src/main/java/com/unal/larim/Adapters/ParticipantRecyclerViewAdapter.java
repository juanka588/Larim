package com.unal.larim.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unal.larim.Data.Participant;
import com.unal.larim.GUI.DetailParticipantActivity;
import com.unal.larim.R;

import java.util.List;

/**
 * Created by JuanCamilo on 15/07/2015.
 */
public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ParticipantViewHolder> {

    private List<Participant> participants;
    private Context context;


    @Override
    public ParticipantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewparticipant, parent, false);
        ParticipantViewHolder pvh = new ParticipantViewHolder(v);
        return pvh;
    }

    @Override
    public void onViewRecycled(ParticipantViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final ParticipantViewHolder holder, int i) {
        Participant p = participants.get(i);
        holder.name.setText(p.toString());
        holder.institution.setText(p.getInstitution());
        holder.country.setText(p.getCountry());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public ParticipantRecyclerViewAdapter(List<Participant> participants, Context context) {
        this.participants = participants;
        this.context = context;
    }

    public void sendEmail(int position) {
        Intent detail = new Intent(context, DetailParticipantActivity.class);
        detail.putExtra(DetailParticipantActivity.PARTICIPANT_ARG, participants.get(position));
        context.startActivity(detail);
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView name;
        TextView country;
        TextView institution;

        ParticipantViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_participant);
            name = (TextView) itemView.findViewById(R.id.textParticipantName);
            country = (TextView) itemView.findViewById(R.id.textNationality);
            institution = (TextView) itemView.findViewById(R.id.textParticipantInstitution);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sendEmail(getAdapterPosition());
        }

    }
}
