package com.burakarslan.yakalacoforcorp.network;

import com.burakarslan.yakalacoforcorp.campaign.CampaignList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CampaignService {

    @GET("product/category/1")
    Call<CampaignList> getCampaign(@Query("start") int start, @Query("count") int count, @Query("fields") String fields);
}
