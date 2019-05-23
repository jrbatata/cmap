package com.junior.cmap.fragments.register;


import android.os.Bundle;
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
public class AlunoFragment extends Fragment {

    public AlunoFragment() {
        // Required empty public constructor
    }

    public static AlunoFragment newInstance(){
        return new AlunoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        Button continuar = (Button) view.findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment responsavel = ResponsavelFragment.newInstance();
                trocaTela(responsavel);
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

}
