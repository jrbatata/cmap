package com.junior.cmap.fragments.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.model.Aluno;
import com.junior.cmap.model.Responsavel;
import com.junior.cmap.model.ViewDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResponsavelFragment extends Fragment {

    private EditText textNome;
    private EditText textCpf;
    private EditText textData;
    private Spinner spinGrauParentesco;
    private RadioButton radioMasculino;
    private Responsavel responsavel;
    private ViewDialog viewDialog;
    private boolean responsavelValido = false;

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
        Button buttonContinuar = (Button) view.findViewById(R.id.buttonContinuar);
        Button buttonVoltar = (Button) view.findViewById(R.id.buttonVoltar);
        responsavel = new Responsavel();
        textNome = (EditText) view.findViewById(R.id.textNome);
        textCpf = (EditText) view.findViewById(R.id.textCpf);
        textData = (EditText) view.findViewById(R.id.dataNasc);
        spinGrauParentesco = (Spinner) view.findViewById(R.id.grauParentesco);
        radioMasculino = (RadioButton) view.findViewById(R.id.radioMasculino);
        viewDialog = new ViewDialog(getActivity());

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.grau_parentesco, android.R.layout.simple_spinner_dropdown_item);
        spinGrauParentesco.setAdapter(adapter);

        spinGrauParentesco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                responsavel.setGrauParentesco(spinGrauParentesco.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                responsavel.setGrauParentesco("");
            }
        });

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment aluno = AlunoFragment.newInstance();
                trocaTela(aluno);
            }
        });

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textNome.getText().toString().isEmpty()) {
                    if (!textCpf.getText().toString().isEmpty()) {
                        if (!textData.getText().toString().isEmpty()) {
                            if (spinGrauParentesco.getSelectedItemPosition() != 0) {
                                viewDialog.showDialog("Validando os dados", "Por favor, aguarde enquanto validamos os dados");
                                if(radioMasculino.isChecked()){
                                    responsavel.setSexo("Masculino");
                                }else{
                                    responsavel.setSexo("Feminino");
                                }

                                responsavel.setNomeCompleto(textNome.getText().toString());
                                responsavel.setCpf(textCpf.getText().toString());
                                responsavel.setDataNasc(textData.getText().toString());

                                Aluno aluno = new Aluno();
                                aluno.getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                                            Aluno al = ds.getValue(Aluno.class);
                                            Log.i("RESPONSAVEL","CPF informado: " + textCpf.getText().toString() + "| Cpf encontrado: " + al.getCpfResponsavel());
                                            if(al.getCpfResponsavel().equals(textCpf.getText().toString())){
                                                responsavelValido = true;
                                            }
                                        }

                                        if(responsavelValido){
                                            Fragment usuario = UsuarioFragment.newInstance();
                                            trocaTela(usuario);
                                        }else{
                                            Toast.makeText(getActivity(), "Erro: O CPF informado não foi identificado como responsável de um aluno", Toast.LENGTH_SHORT).show();
                                        }
                                        viewDialog.hideDialog();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(), "Erro: Selecione o grau de parentesco", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Erro: Preencha a sua data de nascimento", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Erro: Preencha seu CPF", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Erro: Preencha o seu nome", Toast.LENGTH_SHORT).show();
                }
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
