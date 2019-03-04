package com.burakarslan.yakalacoforcorp.campaign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.burakarslan.yakalacoforcorp.R;

import java.util.ArrayList;

public class CampaignActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Campaign> campaignArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        Intent intent=getIntent();
        campaignArrayList= (ArrayList<Campaign>) intent.getSerializableExtra("campaignList");

        recyclerView=findViewById(R.id.rvCampaign);
        adapter=new CampaignAdapter(campaignArrayList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CampaignActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
