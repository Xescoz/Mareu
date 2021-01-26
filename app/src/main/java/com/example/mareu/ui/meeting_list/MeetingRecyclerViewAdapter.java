package com.example.mareu.ui.meeting_list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {


    private final List<Meeting> mMeetings;
    private Activity activity;

    public MeetingRecyclerViewAdapter(List<Meeting> items,Activity activity) {
        mMeetings = items;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.meeting.setText(meeting.getPlace() + " - " +meeting.getHour()+ " - "+meeting.getSubject());
        holder.meetingParticipants.setText(meeting.getParticipants());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView meeting;
        public TextView meetingParticipants;
        public ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            meeting = (TextView) view.findViewById(R.id.meeting);
            meetingParticipants = (TextView) view.findViewById(R.id.meeting_participants);
            deleteButton = (ImageButton) view.findViewById(R.id.delete_meeting);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    try {
                        mMeetings.remove(position);
                        notifyItemRemoved(position);
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                }
            });
        }
    }
}
