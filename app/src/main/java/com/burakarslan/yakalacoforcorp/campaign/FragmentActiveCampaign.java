package com.burakarslan.yakalacoforcorp.campaign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.burakarslan.yakalacoforcorp.R;
import com.burakarslan.yakalacoforcorp.network.CampaignService;
import com.burakarslan.yakalacoforcorp.network.RetrofitInstance;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentActiveCampaign extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvCampaignActive;
    //ProgressDialog progressDialog;
    private ArrayList<Campaign> campaignArrayList;
    private RecyclerView.Adapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_active_campaign,container,false);
        rvCampaignActive=view.findViewById(R.id.rvCampaignActive);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        getCampaigns();



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


        return view;

    }

    @Override
    public void onRefresh() {
        getCampaigns();
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getCampaigns();
    }*/

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
                        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                        rvCampaignActive.setLayoutManager(layoutManager);
                        rvCampaignActive.setAdapter(adapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<CampaignList> call, Throwable t) {
                //progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


}
