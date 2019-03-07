package com.alan.waller.e_bikedatacollection;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionRecyclerViewAdapter.SessionViewHolder> {

    class SessionViewHolder extends RecyclerView.ViewHolder {
        private final TextView sessionItemView;
        private final TextView startTimeView;
        private final TextView endTimeView;

        private SessionViewHolder(View itemView) {
            super(itemView);
            sessionItemView = itemView.findViewById(R.id.nameView);
            startTimeView = itemView.findViewById(R.id.startView);
            endTimeView = itemView.findViewById(R.id.endView);
        }
    }
    private final String TAG = "SRVA";
    private final LayoutInflater mInflater;
    private List<Session> sessionList;

    SessionRecyclerViewAdapter(Context context){ mInflater = LayoutInflater.from(context);}

    void setSessions(List<Session> list){
        sessionList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder Ran");
        if(sessionList != null){
            Session current = sessionList.get(position);
            holder.sessionItemView.setText(current.getSubjectName());
            holder.startTimeView.setText(current.getStartTime() + "");
            holder.endTimeView.setText(current.getEndTime() + "");
        }else{
            holder.sessionItemView.setText("No Session");
        }

    }

    @Override
    public SessionViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        // inflate the recycler_item_view layout
        final View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView startView = itemView.findViewById(R.id.startView);
                TextView endView = itemView.findViewById(R.id.endView);

                long startTime = Long.parseLong(startView.getText().toString());
                long endTime = Long.parseLong(endView.getText().toString());
                //TODO: create session data display activity
                Intent intent = new Intent(itemView.getContext(), insert activity name here.class);
                intent.putExtra("START_TIME", startTime);
                intent.putExtra("END_TIME", endTime);
                itemView.getContext().startActivity(intent);
            }
        });
        return new SessionViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if(sessionList != null)
            return sessionList.size();
        return 0;
    }

}
