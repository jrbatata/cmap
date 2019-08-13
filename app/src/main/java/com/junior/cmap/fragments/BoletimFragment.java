package com.junior.cmap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.cmap.R;
import com.junior.cmap.activity.PrincipalActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoletimFragment extends Fragment {


    public BoletimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boletim, container, false);
    }

}
