package com.junior.cmap.fragments.register;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.junior.cmap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment {


    public UsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

    public static UsuarioFragment newInstance() {
        return new UsuarioFragment();
    }

    public void init(View view) {
        Button finalizar = (Button) view.findViewById(R.id.finalizar);
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cadastro realizado com sucesso")
                        .setMessage("Bem-vindo, usuário!")
                        .setCancelable(true)
                        .create()
                        .show();
            }
        });
    }
}
