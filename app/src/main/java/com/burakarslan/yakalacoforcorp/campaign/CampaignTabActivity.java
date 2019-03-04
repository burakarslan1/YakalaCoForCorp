package com.burakarslan.yakalacoforcorp.campaign;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.burakarslan.yakalacoforcorp.R;

public class CampaignTabActivity extends AppCompatActivity {

    SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private MenuInflater menuInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_tab);

        sectionsPageAdapter=new SectionsPageAdapter(getSupportFragmentManager());
        viewPager=findViewById(R.id.container);
        setUpPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setUpPager(ViewPager viewPager){
        SectionsPageAdapter adapter=new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentActiveCampaign(),"Aktif Kampanyalar");
        adapter.addFragment(new FragmentPassiveCampaign(),"Geçmiş Kampanyalar");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_campaign, menu);

        return true;
    }
}
