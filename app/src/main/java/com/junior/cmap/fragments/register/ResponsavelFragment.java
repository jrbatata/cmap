package com.junior.cmap.fragments.register;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.junior.cmap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResponsavelFragment extends Fragment {


    public ResponsavelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_responsavel, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        Button continuar = (Button) view.findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment usuario = UsuarioFragment.newInstance();
                trocaTela(usuario);
            }
        });
    }

    public void trocaTela(Fragment fragment) {
        //Declaração e inicialização da transação
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //Substitui o fragment colocado dentro do container
        transaction.replace(R.id.container, fragment);
        //Adiciona a transação na pilha
        transaction.addToBackStack(null);
        //Fecha a transação
        transaction.commit();
    }

    public static ResponsavelFragment newInstance() {
        return new ResponsavelFragment();
    }
}
