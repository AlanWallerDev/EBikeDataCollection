package com.alan.waller.e_bikedatacollection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SessionRecyclerViewAdapter extends RecyclerView.Adapter<SessionRecyclerViewAdapter.SessionViewHolder> {

    class SessionViewHolder extends RecyclerView.ViewHolder{

        private final TextView sessionItemView;

        private SessionViewHolder(View itemHolder){
            super(itemView);
            sessionItemView = (TextView) itemView.findViewById(R.id.nameView);
        }

    }

    private final LayoutInflater mInflater;
    private List<Session> sessionList; //

    SessionRecyclerViewAdapter(Context context){ mInflater = LayoutInflater.from(context);}

    void setSessions(List<Session> list){
        sessionList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        if(sessionList != null){
            Session current = sessionList.get(position);
            holder.sessionItemView.setText(current.getSubjectName());
        }else{
            holder.sessionItemView.setText("No Session");
        }

    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // inflate the recycler_item_view layout
        View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new SessionViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if(sessionList != null)
            return sessionList.size();
        return 0;
    }

}
