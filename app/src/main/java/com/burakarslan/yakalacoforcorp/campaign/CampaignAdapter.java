package com.burakarslan.yakalacoforcorp.campaign;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.burakarslan.yakalacoforcorp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder> {

    private ArrayList<Campaign> dataList;

    public CampaignAdapter(ArrayList<Campaign> dataList){
        this.dataList=dataList;
    }

    @NonNull
    @Override
    public CampaignAdapter.CampaignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_campaign,parent,false);
        return new CampaignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CampaignAdapter.CampaignViewHolder holder, int position) {
        Picasso.get().load(String.valueOf(dataList.get(position).getImage_urls().get(0))).into(holder.ivCampaignImage);
        holder.tvCampaignName.setText(dataList.get(position).getName());
        holder.tvCampaignPrice.setText(String.valueOf(dataList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class CampaignViewHolder extends RecyclerView.ViewHolder{
        TextView tvCampaignName,tvCampaignPrice;
        ImageView ivCampaignImage;

        CampaignViewHolder(View itemView){
            super(itemView);
            tvCampaignName=itemView.findViewById(R.id.tvCampaignName);
            tvCampaignPrice=itemView.findViewById(R.id.tvCampaignPrice);
            ivCampaignImage=itemView.findViewById(R.id.ivCampaignImage);

        }
    }
}
