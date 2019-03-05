package com.burakarslan.yakalacoforcorp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.burakarslan.yakalacoforcorp.cache.SharedPref;
import com.burakarslan.yakalacoforcorp.campaign.CampaignActivity;
import com.burakarslan.yakalacoforcorp.codevalidation.CodeActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 1;
    private ProgressDialog progressDialog;
    private String token;
    private Button btnInfo,btnCampaign,btnLogout,btnScanner;
    public static TextView tvQrCodeResult;


    PieChart mChart;
    public String[] xValues={"Satılan","Kalan","İptal Edilen"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnInfo=findViewById(R.id.btnInfo);
        btnCampaign=findViewById(R.id.btnCampaign);
        btnLogout=findViewById(R.id.btnLogout);
        btnScanner=findViewById(R.id.btnScanner);
        tvQrCodeResult=findViewById(R.id.tv_qr_result);

        if(SharedPref.getMyInstance().getToken()!=null){
            token=SharedPref.getMyInstance().getToken();
        }else{
            Intent intent=getIntent();
            token=intent.getStringExtra("token");
        }

        mChart=findViewById(R.id.pieChart);
        fillDataToChart();

    }

    public void fillDataToChart() {
        ArrayList<PieEntry> entries=new ArrayList<>();
        entries.add(new PieEntry(11,0));
        entries.add(new PieEntry(2,0));

        PieDataSet dataSet=new PieDataSet(entries,"asd");
        PieData data=new PieData(dataSet);

        dataSet.setColors(Color.DKGRAY, Color.GRAY, Color.GREEN);
        dataSet.setSliceSpace(5f);
        dataSet.setValueTextSize(14f);
        mChart.setUsePercentValues(true);
        mChart.setDrawHoleEnabled(false);
        mChart.setData(data);
        mChart.invalidate();
    }

    public void getMyCampaigns(View view){
        //getCampaigns();
        Intent intent=new Intent(getApplicationContext(), CampaignActivity.class);
        startActivity(intent);
    }

    public void getMyInfo(View view){

    }

    /*private void getCampaigns() {
        progressDialog=new ProgressDialog(DashboardActivity.this);
        progressDialog.setMessage("Lütfen bekleyin...");
        progressDialog.show();

        String fields= "id,name,price,image_urls";

        CampaignService campaignService= RetrofitInstance.getRetrofitInstance().create(CampaignService.class);

        Call<CampaignList> call=campaignService.getCampaign(1,10,fields);

        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<CampaignList>() {
            @Override
            public void onResponse(Call<CampaignList> call, Response<CampaignList> response) {
                if(response.body()!=null){
                    if(response.code()== HttpURLConnection.HTTP_OK){
                        progressDialog.dismiss();
                        ArrayList<Campaign> campaignDataList=response.body().getCampaignList();
                        Intent intent=new Intent(getApplicationContext(),CampaignActivity.class);
                        intent.putExtra("campaignList",campaignDataList);
                        startActivity(intent);


                    }
                }
            }

            @Override
            public void onFailure(Call<CampaignList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void logout(View view){

        //Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(i);

        SharedPref.getMyInstance().setToken(null);
        SharedPref.getMyInstance().setPassword(null);
        SharedPref.getMyInstance().setEmail(null);
        finish();
    }

    @Override
    public void onBackPressed(){
        backButtonHandler();
    }

    private void backButtonHandler() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setTitle("Uygulamadan çıkış");
        alertDialog.setMessage("Uygulamadan çıkmak istiyor musunuz?");

        alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
            }
        });

        alertDialog.show();
    }

    /*public void goTabs(View view){
        Intent intent=new Intent(getApplicationContext(),CampaignTabActivity.class);
        startActivity(intent);
    }*/

    public void gotoCodeScanner(View view){

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},MY_PERMISSION_REQUEST_CAMERA);

        }else {
            runScanner();
        }
    }

    public void runScanner(){
        startActivity(new Intent(getApplicationContext(), CodeActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_CAMERA:

                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    runScanner();
                }else {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                        new AlertDialog.Builder(this)
                                .setTitle("Kamera izni")
                                .setMessage("Kod tarayıcıyı kullanabilmeniz için uygulamanın kamera erişimine izin vermeniz gerekmektedir." +
                                        "Tekrar deneyin ve gerekli izni onaylayın lütfen.").show();
                    }else {
                        new AlertDialog.Builder(this)
                                .setTitle("Kamera izni reddedildi.")
                                .setMessage("Uygulamanın kamera erişim iznini reddettiniz. Lütfen ayarlardan aktif hale getirin.").show();
                    }
                }

                break;
        }
    }
}
