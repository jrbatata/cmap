package com.junior.cmap.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.model.Aluno;
import com.junior.cmap.model.Usuario;
import com.junior.cmap.model.ViewDialog;

import java.util.InputMismatchException;

public class CadastroActivity extends AppCompatActivity {

    private Aluno alunoSelecionado;
    private Usuario usuario;
    private EditText textNome;
    private EditText textEmail;
    private EditText textCpf;
    private EditText textSenha;
    private EditText textConfirmar;
    private Spinner spinGrauParentesco;
    private RadioButton radioMasculino;
    private CheckBox checkTermos;
    private ViewDialog viewDialog;
    private DatabaseReference usuariosReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        init();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CadastroActivity.this, SelecionarAlunoActivity.class));
    }

    public void init(){
        //Configura a ActionBar
        getSupportActionBar().setTitle("Registrar-se");

        //Inicializa a referência ao Firebase
        usuariosReference = ConfiguracaoFirebase.getFirebase().child("cmap/usuarios");
        firebaseAuth = ConfiguracaoFirebase.getFireBaseAuth();

        //Recupera aluno selecionado
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            alunoSelecionado = (Aluno) bundle.getSerializable("alunoSelecionado");

            //Inicializa botões do formulário
            Button buttonFinalizar = (Button) findViewById(R.id.buttonFinalizar);
            Button buttonVoltar = (Button) findViewById(R.id.buttonVoltar);
            viewDialog = new ViewDialog(this);

            //Inicializa eventos de clique
            buttonVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            buttonFinalizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registrarUsuario();
                }
            });

            //Inicializa campos do formulário
            textNome = (EditText) findViewById(R.id.textNome);
            textEmail = (EditText) findViewById(R.id.textEmail);
            textCpf = (EditText) findViewById(R.id.textCpf);
            textSenha = (EditText) findViewById(R.id.textSenha);
            textConfirmar = (EditText) findViewById(R.id.textConfirmar);
            spinGrauParentesco = (Spinner) findViewById(R.id.grauParentesco);
            radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
            checkTermos = (CheckBox) findViewById(R.id.checkTermos);

            //Inicializa Usuário
            usuario = new Usuario();
            usuario.setMatAluno(alunoSelecionado.getMatricula());

            //Inicializa Spinner
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.grau_parentesco,
                    android.R.layout.simple_spinner_dropdown_item);
            spinGrauParentesco.setAdapter(adapter);
            spinGrauParentesco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    usuario.setGrauParentesco(spinGrauParentesco.getSelectedItem().toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    usuario.setGrauParentesco("");
                }
            });


        }
    }

    public void registrarUsuario(){
        //Verifica se os campos estão preenchidos
        if (!textNome.getText().toString().isEmpty()) {
            if (!textEmail.getText().toString().isEmpty()) {
                if (!textCpf.getText().toString().isEmpty() && validaCPF(textCpf.getText().toString())) {
                    if(textCpf.getText().toString().equals(alunoSelecionado.getCpfResponsavel())){
                        if(spinGrauParentesco.getSelectedItemPosition() != 0){
                            if(!textSenha.getText().toString().isEmpty()) {
                                if (textSenha.getText().toString().equals(textConfirmar.getText().toString())) {
                                    if(checkTermos.isChecked()) {
                                        //Exibe Dialog de Processamento
                                        viewDialog.showDialog("Validando os dados", "Por favor, aguarde enquanto validamos os dados");

                                        //Verifica o Radio selecionado
                                        if (radioMasculino.isChecked()) {
                                            usuario.setSexo("Masculino");
                                        } else {
                                            usuario.setSexo("Feminino");
                                        }

                                        //Inicializa o usuário
                                        usuario.setNomeCompleto(textNome.getText().toString());
                                        usuario.setEmail(textEmail.getText().toString());
                                        usuario.setCpf(textCpf.getText().toString());

                                        //Registra usuário no FirebaseAuth
                                        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        viewDialog.hideDialog();
                                                        if(task.isSuccessful()){
                                                            String uid = firebaseAuth.getCurrentUser().getUid();
                                                            usuario.setId(uid);
                                                            usuariosReference.child(usuario.getId()).setValue(usuario);
                                                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                                            Log.i("CadastroUsuario", "Id: " + usuario.getId());
                                                            Log.i("CadastroUsuario", "Nome: " + usuario.getNomeCompleto());
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean validaCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
