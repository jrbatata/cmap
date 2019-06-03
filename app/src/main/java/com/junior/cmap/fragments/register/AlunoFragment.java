package com.junior.cmap.fragments.register;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.junior.cmap.model.ViewDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlunoFragment extends Fragment {

    private EditText textNome;
    private EditText textMatricula;
    private EditText textData;
    private Spinner spinNivel;
    private RadioButton radioMasculino;
    private Aluno aluno;
    private ViewDialog viewDialog;

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
        aluno = new Aluno();
        textNome = (EditText) view.findViewById(R.id.textNome);
        textMatricula = (EditText) view.findViewById(R.id.textMatricula);
        textData = (EditText) view.findViewById(R.id.textData);
        spinNivel = (Spinner) view.findViewById(R.id.spinNivel);
        radioMasculino = (RadioButton) view.findViewById(R.id.radioMasculino);
        viewDialog = new ViewDialog(getActivity());

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.nivel, android.R.layout.simple_spinner_dropdown_item);
        spinNivel.setAdapter(adapter);

        spinNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aluno.setNivelEscolar(spinNivel.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                aluno.setNivelEscolar("");
            }
        });

        Button buttonContinuar = (Button) view.findViewById(R.id.buttonContinuar);
        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textNome.getText().toString().isEmpty()){
                    if(!textMatricula.getText().toString().isEmpty()){
                        if(!textData.getText().toString().isEmpty()){
                            if(spinNivel.getSelectedItemPosition() != 0){
                                viewDialog.showDialog("Validando os dados", "Por favor, aguarde enquanto validamos os dados");
                                if(radioMasculino.isChecked()){
                                    aluno.setSexo("Masculino");
                                }else{
                                    aluno.setSexo("Feminino");
                                }

                                aluno.setNomeCompleto(textNome.getText().toString());
                                aluno.setMatricula(textMatricula.getText().toString());
                                aluno.setDataNasc(textData.getText().toString());

                                aluno.getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        boolean alunoMatriculado = false;
                                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                                            Aluno al = ds.getValue(Aluno.class);
                                            Log.i("ALUNOO","Matrícula informada: " + aluno.getMatricula() + " | Matrícula encontrada no sistema: " + al.getMatricula());
                                            if(aluno.getMatricula().equals(al.getMatricula())){
                                                alunoMatriculado = true;
                                            }
                                        }

                                        if(alunoMatriculado){
                                            Fragment responsavel = ResponsavelFragment.newInstance();
                                            trocaTela(responsavel);
                                        }else{
                                            Toast.makeText(getActivity(), "Erro: Não foi encontrado no sistema nenhum aluno com a matrícula informada", Toast.LENGTH_SHORT).show();
                                        }
                                        viewDialog.hideDialog();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(), "Erro: Selecione um nível escolar", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Erro: Preencha a data de nascimento corretamente", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Erro: Preencha a matrícula corretamente", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Erro: Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void trocaTela(Fragment fragment) {
        //Declaração e inicialização da transação
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //Substitui o fragment colocado dentro do container
        transaction.replace(R.id.container, fragment, "Responsavel");
        //Adiciona a transação na pilha
        transaction.addToBackStack(null);
        //Fecha a transação
        transaction.commit();
    }


}
