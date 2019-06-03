package com.junior.cmap.fragments.register;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.junior.cmap.R;
import com.junior.cmap.activity.MenuActivity;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.ViewDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuarioFragment extends Fragment{

    private EditText textEmail;
    private EditText textSenha;
    private EditText textConfirmar;
    private CheckBox checkTermos;
    private FirebaseAuth auth = ConfiguracaoFirebase.getFireBaseAuth();
    private ViewDialog viewDialog;
    private String strStatus = "";

    public UsuarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        init(view);
        return view;
    }

    public static UsuarioFragment newInstance() {
        return new UsuarioFragment();
    }

    public void init(View view) {
        Button buttonFinalizar = (Button) view.findViewById(R.id.buttonFinalizar);
        Button buttonVoltar = (Button) view.findViewById(R.id.buttonVoltar);
        textEmail = (EditText) view.findViewById(R.id.textEmail);
        textSenha = (EditText) view.findViewById(R.id.textSenha);
        textConfirmar = (EditText) view.findViewById(R.id.textConfirmar);
        checkTermos = (CheckBox) view.findViewById(R.id.checkTermos);
        viewDialog = new ViewDialog(getActivity());

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment responsavel = ResponsavelFragment.newInstance();
                trocaTela(responsavel);
            }
        });

        buttonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = textEmail.getText().toString();
                String strSenha = textSenha.getText().toString();
                String strConfirmarSenha = textConfirmar.getText().toString();

                if(!strEmail.isEmpty() && !strSenha.isEmpty() && !strConfirmarSenha.isEmpty()) {
                    if (strSenha.equals(strConfirmarSenha)) {
                        if (checkTermos.isChecked()) {
                            viewDialog.showDialog("Autenticando", "Por favor, aguarde enquanto registramos seus dados");
                            auth.createUserWithEmailAndPassword(textEmail.getText().toString(), textSenha.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    viewDialog.hideDialog();
                                    if (task.isSuccessful()) {
                                        strStatus = "Cadastro realizado com sucesso!";
                                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                                        startActivity(intent);
                                    } else {
                                        strStatus = "Erro: " + task.getException().getMessage();
                                    }
                                }
                            });
                        } else { strStatus = "Erro: Você precisa concordar com os termos de privacidade para registrar-se"; }
                    } else { strStatus = "Erro: As senhas não correspondem entre si"; }
                }else{ strStatus = "Erro: Você precisa preencher todos os campos para registrar-se"; }
                Toast.makeText(getActivity(), strStatus, Toast.LENGTH_SHORT).show();
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
