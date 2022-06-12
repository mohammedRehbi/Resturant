package com.example.Resturant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CaptionedReportAdapter
        extends RecyclerView.Adapter<CaptionedReportAdapter.ViewHolder>{
    private Context context;
    private List<Report> items;


    public CaptionedReportAdapter(Context context, List<Report> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Report reports = items.get(position);
        CardView cardView = holder.cardView;
        TextView txtName =(TextView)cardView.findViewById(R.id.txtName);
        txtName.setText("Customer Rate :" +reports.getRate());
        TextView txtEmail = (TextView)cardView.findViewById(R.id.txtEmail);
        txtEmail.setText("Customer Comment :"+reports.getComment());
        TextView txtId = (TextView)cardView.findViewById(R.id.txtId);
        txtId.setText("Customer Name :" +reports.getUserName());
        TextView txtRoomNo = (TextView)cardView.findViewById(R.id.txtRoomNo);
        txtRoomNo.setVisibility(View.GONE);
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }
}

