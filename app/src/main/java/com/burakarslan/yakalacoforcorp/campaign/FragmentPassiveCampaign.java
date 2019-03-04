package com.burakarslan.yakalacoforcorp.campaign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.burakarslan.yakalacoforcorp.R;

public class FragmentPassiveCampaign extends Fragment {
    TextView tvPassive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_passive_campaign,container,false);
        tvPassive=view.findViewById(R.id.tvPassive);

        return view;
    }
}
