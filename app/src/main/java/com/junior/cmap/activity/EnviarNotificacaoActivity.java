package com.junior.cmap.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.support.v7.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.adapter.AdapterPesquisa;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.config.RecyclerItemClickListener;
import com.junior.cmap.model.Aluno;
import com.junior.cmap.model.Notificacao;
import com.junior.cmap.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class EnviarNotificacaoActivity extends AppCompatActivity {

    private Usuario usuarioAtual;
    private TextView pesquisarEmail;
    private TextView selecionarCurso;
    private TextView alunosSelecionados;
    private TextView selecionados;
    private SearchView searchViewPesquisa;
    private EditText textTitulo;
    private EditText textMensagem;
    private RadioButton radioTodos;
    private RadioButton radioCurso;
    private RadioButton radioEspecificar;
    private Spinner spinCurso;
    private Button buttonEnviar;
    private RecyclerView recyclerViewAlunos;
    private List<Aluno> listaAlunos;
    private AdapterPesquisa adapterPesquisa;
    private DatabaseReference alunosRef;
    List<String> listEspecificos;
    private String txtSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_notificacao);
        iniciar();
    }

    private void iniciar() {
        getSupportActionBar().setTitle("Enviar Notificação");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("cmap").child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioAtual = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pesquisarEmail = findViewById(R.id.pesquisarEmail);
        selecionarCurso = findViewById(R.id.selecionarCurso);
        alunosSelecionados = findViewById(R.id.alunosSelecionados);
        selecionados = findViewById(R.id.selecionados);
        searchViewPesquisa = findViewById(R.id.searchViewPesquisa);
        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        textTitulo = findViewById(R.id.textTitulo);
        textMensagem = findViewById(R.id.textMensagem);
        radioTodos = findViewById(R.id.radioTodos);
        radioCurso = findViewById(R.id.radioCurso);
        radioEspecificar = findViewById(R.id.radioEspecificar);
        spinCurso = findViewById(R.id.spinCurso);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        alunosRef = ConfiguracaoFirebase.getFirebase().child("sigaa/alunos");
        txtSelecionado = "";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.curso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCurso.setAdapter(adapter);

        listaAlunos = new ArrayList<>();
        listEspecificos = new ArrayList<>();

        recyclerViewAlunos.setHasFixedSize(false);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        //Configura Adapter
        adapterPesquisa = new AdapterPesquisa(listaAlunos, this);
        recyclerViewAlunos.setAdapter(adapterPesquisa);
        recyclerViewAlunos.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Configura evento de clique
        recyclerViewAlunos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewAlunos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Aluno alunoSelecionado = listaAlunos.get(position);
                txtSelecionado += alunoSelecionado.getNome();
                txtSelecionado += "\n";
                selecionados.setText(txtSelecionado);
                listEspecificos.add(alunoSelecionado.getCpfResponsavel());
                Toast.makeText(EnviarNotificacaoActivity.this, "Adicionado a Lista", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        //Configura SearchView
        searchViewPesquisa.setQueryHint("Buscar pelo nome");
        searchViewPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String textoDigitado = s.toUpperCase();
                pesquisarAlunos(textoDigitado);
                return true;
            }
        });

        radioTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(0);
            }
        });

        radioCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(1);
            }
        });

        radioEspecificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioSelecionado(2);
            }
        });

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("cmap");

                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");

                final Notificacao notificacao = new Notificacao();
                notificacao.setDataHora(formatador.format(calendar.getTime()));
                notificacao.setId(notificacao.getDataHora() + "-" + UUID.randomUUID().toString());
                notificacao.setServidorId(usuarioAtual.getId());
                notificacao.setTitulo(textTitulo.getText().toString());
                notificacao.setLida(false);
                notificacao.setDescricao(textMensagem.getText().toString());

                if(radioTodos.isChecked()){
                    ref.child("usuarios").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Usuario u = ds.getValue(Usuario.class);
                                    if(!u.isAdm()){
                                        notificacao.setPrivacidade("Publico");
                                        ref.child("usuarios").child(u.getId()).child("notificacoes").child(notificacao.getId()).setValue(notificacao);
                                        notificacao.setLida(true);
                                        ref.child("usuarios").child(usuarioAtual.getId()).child("notificacoes").child(notificacao.getId()).setValue(notificacao);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    if(radioCurso.isChecked()){
                        ConfiguracaoFirebase.getFirebase().child("sigaa/alunos").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String curso = spinCurso.getSelectedItem().toString();
                                List<String> listSelecionados = new ArrayList<>();

                                for(DataSnapshot ds : dataSnapshot.getChildren()){
                                    Aluno al = ds.getValue(Aluno.class);
                                    if(al.getCurso().equals(curso)){
                                        //usando o id do responsavel no lugar do cpf por preguiça
                                        listSelecionados.add(al.getCpfResponsavel());
                                    }
                                }

                                enviarSelecionados(listSelecionados, notificacao);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else{
                        if(radioEspecificar.isChecked()){
                            enviarSelecionados(listEspecificos, notificacao);
                        }
                    }
                }
            }
        });
    }

    private void enviarSelecionados(List<String> list, Notificacao notificacao) {
        for(int i = 0; i < list.size(); i++){
            FirebaseDatabase.getInstance().getReference().child("cmap/usuarios").child(list.get(i)).child("notificacoes").child(notificacao.getId()).setValue(notificacao);
        }
    }

    private void radioSelecionado(int i) {
        switch (i){
            case 0:
                pesquisarEmail.setVisibility(View.GONE);
                searchViewPesquisa.setVisibility(View.GONE);
                selecionarCurso.setVisibility(View.GONE);
                alunosSelecionados.setVisibility(View.GONE);
                selecionados.setVisibility(View.GONE);
                spinCurso.setVisibility(View.GONE);
                radioTodos.setChecked(true);
                radioCurso.setChecked(false);
                radioEspecificar.setChecked(false);
                break;
            case 1:
                pesquisarEmail.setVisibility(View.GONE);
                searchViewPesquisa.setVisibility(View.GONE);
                alunosSelecionados.setVisibility(View.GONE);
                selecionados.setVisibility(View.GONE);
                selecionarCurso.setVisibility(View.VISIBLE);
                spinCurso.setVisibility(View.VISIBLE);
                radioCurso.setChecked(true);
                radioTodos.setChecked(false);
                radioEspecificar.setChecked(false);
                break;
            case 2:
                pesquisarEmail.setVisibility(View.VISIBLE);
                searchViewPesquisa.setVisibility(View.VISIBLE);
                alunosSelecionados.setVisibility(View.VISIBLE);
                selecionados.setVisibility(View.VISIBLE);
                selecionarCurso.setVisibility(View.GONE);
                spinCurso.setVisibility(View.GONE);
                radioEspecificar.setChecked(true);
                radioCurso.setChecked(false);
                radioTodos.setChecked(false);
                break;
        }
    }

    public void pesquisarAlunos(String texto){
        //Limpa a Lista
        listaAlunos.clear();

        //Pesquisa aluno caso tenha texto na pesquisa
        if(texto.length() > 0){
            recyclerViewAlunos.setVisibility(View.VISIBLE);

            Query query = alunosRef.orderByChild("nome")
                    .startAt(texto)
                    .endAt(texto + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        listaAlunos.add(ds.getValue(Aluno.class));
                    }

                    adapterPesquisa.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            recyclerViewAlunos.setVisibility(View.GONE);
        }
    }

}
