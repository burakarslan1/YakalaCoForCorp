package com.burakarslan.yakalacoforcorp.campaign;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CampaignList {
    @SerializedName("products")
    private ArrayList<Campaign> campaignList;

    public ArrayList<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(ArrayList<Campaign> campaignList) {
        this.campaignList = campaignList;
    }
}
