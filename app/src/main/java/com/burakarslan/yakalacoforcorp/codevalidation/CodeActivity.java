package com.burakarslan.yakalacoforcorp.codevalidation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.burakarslan.yakalacoforcorp.DashboardActivity;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;
     private static final int  MY_PERMISSION_CAMERA_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView=new ZXingScannerView(this);
        setContentView(zXingScannerView);

    }

    @Override
    public void handleResult(Result rawResult) {

        DashboardActivity.tvQrCodeResult.setText(rawResult.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

}
