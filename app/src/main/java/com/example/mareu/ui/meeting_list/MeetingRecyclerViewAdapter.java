package com.example.mareu.ui.meeting_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.databinding.FragmentMeetingBinding;
import com.example.mareu.model.Meeting;


import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {


    private final List<Meeting> mMeetings;
    private final Context context;

    public MeetingRecyclerViewAdapter(List<Meeting> items, Context context) {
        mMeetings = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Meeting meeting = mMeetings.get(position);
        holder.binding.meeting.setText(context.getString(R.string.meeting, meeting.getPlace(),meeting.getHour(),meeting.getSubject()));
        holder.binding.meetingParticipants.setText(meeting.getParticipants());
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentMeetingBinding binding;
        public ViewHolder(FragmentMeetingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.deleteMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    mMeetings.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
