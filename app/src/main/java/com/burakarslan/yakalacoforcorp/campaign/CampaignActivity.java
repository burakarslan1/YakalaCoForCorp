package com.burakarslan.yakalacoforcorp.campaign;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.burakarslan.yakalacoforcorp.R;
import com.burakarslan.yakalacoforcorp.network.CampaignService;
import com.burakarslan.yakalacoforcorp.network.RetrofitInstance;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Campaign> campaignArrayList;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        //Intent intent=getIntent();
        //campaignArrayList= (ArrayList<Campaign>) intent.getSerializableExtra("campaignList");

        recyclerView=findViewById(R.id.rvCampaign);
        mSwipeRefreshLayout=findViewById(R.id.activity_campaign_swipe);
        getCampaigns();
        //adapter=new CampaignAdapter(campaignArrayList);
        //RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(CampaignActivity.this);
        //recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getCampaigns();
            }
        });
    }

    private void getCampaigns() {

        mSwipeRefreshLayout.setRefreshing(true);

       /* progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Lütfen bekleyin...");
        progressDialog.show();*/

        String fields= "id,name,price,image_urls";

        CampaignService campaignService= RetrofitInstance.getRetrofitInstance().create(CampaignService.class);

        Call<CampaignList> call=campaignService.getCampaign(1,10,fields);

        Log.d("URL Called", call.request().url() + "");

        call.enqueue(new Callback<CampaignList>() {
            @Override
            public void onResponse(Call<CampaignList> call, Response<CampaignList> response) {
                if(response.body()!=null){
                    if(response.code()== HttpURLConnection.HTTP_OK){
                        //progressDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                        ArrayList<Campaign> campaignDataList=response.body().getCampaignList();
                        //Intent intent=new Intent(getApplicationContext(),CampaignActivity.class);
                        //intent.putExtra("campaignList",campaignDataList);
                        //startActivity(intent);
                        //Toast.makeText(getContext(),"asdasd",Toast.LENGTH_SHORT).show();

                        adapter=new CampaignAdapter(campaignDataList);
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<CampaignList> call, Throwable t) {
                //progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        getCampaigns();
    }
}
