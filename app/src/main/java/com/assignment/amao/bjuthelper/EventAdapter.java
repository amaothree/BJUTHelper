package com.assignment.amao.bjuthelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    private int resourceId;

    public EventAdapter(Context context, int ResourceId, List<Event> events){
        super(context,ResourceId,events);
        resourceId = ResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Event event = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.address = (TextView) view.findViewById(R.id.item_address);
            viewHolder.name = (TextView) view.findViewById(R.id.item_user);
            viewHolder.title = (TextView) view.findViewById(R.id.item_title);
            viewHolder.money = (TextView) view.findViewById(R.id.item_money);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        TextView eventAddress = (TextView) view.findViewById(R.id.item_address);
        TextView eventUser = (TextView) view.findViewById(R.id.item_user);
        TextView eventTitle = (TextView) view.findViewById(R.id.item_title);
        TextView eventMoney = (TextView) view.findViewById(R.id.item_money);

        eventAddress.setText(event.getAddress());
        eventTitle.setText(event.getTitle());
        eventMoney.setText("$"+event.getMoney());
        eventUser.setText(event.getCustomerName());

        return view;
    }

    class ViewHolder {
        TextView title;
        TextView name;
        TextView money;
        TextView address;
    }
}
