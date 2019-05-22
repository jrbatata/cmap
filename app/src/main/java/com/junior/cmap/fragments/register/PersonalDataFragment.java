package com.junior.cmap.fragments.register;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.cmap.R;
import com.kofigyan.stateprogressbar.StateProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDataFragment extends Fragment {


    public PersonalDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_data, container, false);
            String[] descriptionData = {"Responsável", "Aluno", "Usuário"};

            StateProgressBar stateProgressBar = (StateProgressBar) view.findViewById(R.id.progressRegister);
            stateProgressBar.setStateDescriptionData(descriptionData);
        return view;
    }


    public static PersonalDataFragment newInstance() {
        return new PersonalDataFragment();
    }
}
