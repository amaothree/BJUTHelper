package com.assignment.amao.bjuthelper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView status;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            name = (TextView) view.findViewById(R.id.item_user);
            money = (TextView) view.findViewById(R.id.item_money);
            address = (TextView) view.findViewById(R.id.item_address);
            status = (TextView) view.findViewById(R.id.item_status);
        }
    }

   public EventAdapter(List<Event> eventList){
        mEventlist =eventList;
   }

   public void updateList(List<Event> eventList){
       mEventlist.clear();
       mEventlist.addAll(eventList);
   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext ==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);
        final ViewHolder holder =new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = mEventlist.get(holder.getAdapterPosition());
                Intent intent = new Intent(mContext,EventContentActivity.class);
                intent.putExtra("id",event.getObjectId());
                intent.putExtra("hid",event.getHelperId());
                intent.putExtra("cid",event.getCustomerId());
                intent.putExtra("status",event.getStatus());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = mEventlist.get(position);
        holder.name.setText(event.getCustomerName());
        holder.address.setText(event.getAddress());
        holder.title.setText(event.getTitle());
        holder.money.setText("￥"+event.getMoney());
        holder.status.setText(event.getStatus());
    }

    @Override
    public int getItemCount() {
        return mEventlist.size();
    }
}
