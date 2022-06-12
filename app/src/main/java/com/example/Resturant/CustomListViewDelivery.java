package com.example.Resturant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;


public class CustomListViewDelivery extends ArrayAdapter<String>{

    private Activity context;

    private String[] roomNumber;
    private String[] price;


    public CustomListViewDelivery(Activity context,String[] roomNumber,String[] price)  {
        super(context, R.layout.layout2,roomNumber);
        this.context=context;
        this.roomNumber=roomNumber;
        this.price=price;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r=convertView;
        final CustomListViewChef.ViewHolder viewHolder;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout2,null,true);
            viewHolder=new CustomListViewChef.ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(CustomListViewChef.ViewHolder)r.getTag();

        }

        viewHolder.tvRoomNumber.setText(roomNumber[position]);
        viewHolder.tvPrice.setText(price[position]);
        viewHolder.textView$.setText("Customer: ");

        return r;
    }

    static class ViewHolder{

        TextView tvRoomNumber;
        TextView tvPrice, textView$;

        ViewHolder(View v){
            tvRoomNumber=(TextView)v.findViewById(R.id.textViewRoomNumber);
            tvPrice=(TextView)v.findViewById(R.id.textViewPrice);
            textView$= (TextView) v.findViewById(R.id.textView3);

        }


    }

}
