package com.example.mareu.ui.list;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.databinding.ItemMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;


import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> meetings;
    private final Context context;
    MeetingApiService apiService = DI.getMeetingApiService();

    public MeetingRecyclerViewAdapter(List<Meeting> meetings, Context context) {
        this.meetings = meetings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.binding.meeting.setText(context.getString(R.string.meeting, meeting.getPlace(), meeting.getHour(), meeting.getSubject()));
        holder.binding.meetingParticipants.setText(meeting.getParticipants());
        holder.binding.meetingCircle.setColorFilter(meeting.getColor(), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMeetingBinding binding;

        public ViewHolder(ItemMeetingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.deleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    apiService.deleteMeeting(meetings.get(position));
                    notifyDataSetChanged();
                }
            });
        }
    }
}
