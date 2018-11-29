package com.assignment.amao.bjuthelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context mContext;

    private List<Event> mEventlist;

    static class  ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView name;
        TextView money;
        TextView address;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            name = (TextView) view.findViewById(R.id.item_user);
            money = (TextView) view.findViewById(R.id.item_money);
            address = (TextView) view.findViewById(R.id.item_address);
        }
    }

   public EventAdapter(List<Event> eventList){
        mEventlist =eventList;
   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext ==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventlist.get(position);
        holder.name.setText(event.getCustomerName());
        holder.address.setText(event.getAddress());
        holder.title.setText(event.getTitle());
        holder.money.setText("￥"+event.getMoney());
    }

    @Override
    public int getItemCount() {
        return mEventlist.size();
    }
}
