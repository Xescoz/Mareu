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
import com.example.mareu.databinding.ActivityAddMeetingBinding;
import com.example.mareu.databinding.ActivityListMeetingBinding;
import com.example.mareu.databinding.FragmentMeetingBinding;
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
        return new ViewHolder(FragmentMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.binding.meeting.setText(meeting.getPlace() + " - " +meeting.getHour()+ " - "+meeting.getSubject());
        holder.binding.meetingParticipants.setText(meeting.getParticipants());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentMeetingBinding binding;
        public ViewHolder(FragmentMeetingBinding b) {
            super(b.getRoot());
            binding = b;

            binding.deleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    try {
                        mMeetings.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                }
            });
        }
    }
}
