package com.junior.cmap.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.junior.cmap.R;
import com.junior.cmap.config.ConfiguracaoFirebase;
import com.junior.cmap.config.NotificacoesAdapter;
import com.junior.cmap.model.Aluno;
import com.junior.cmap.model.Notificacao;
import com.junior.cmap.model.Responsavel;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Responsavel responsavel;
    private RecyclerView recyclerMensagens;
    private NotificacoesAdapter adapter;
    private List<Notificacao> notificacoes;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity2, menu);
        inflater.inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuPesquisar:
                break;
            case R.id.menuSair:
                try{
                    FirebaseAuth auth = ConfiguracaoFirebase.getFireBaseAuth();
                    auth.signOut();
                    abrirTela(MainActivity.class);
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        getSupportActionBar().hide();//setTitle("Caixa de Mensagens");

        responsavel = new Responsavel();
        notificacoes = new ArrayList<>();
        recyclerMensagens = (RecyclerView) findViewById(R.id.recyclerMensagens);
        context = this;

        responsavel.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Responsavel rs = ds.getValue(Responsavel.class);
                    Toast.makeText(PrincipalActivity.this, "Bem-vindo de volta, " + rs.getNomeCompleto()+ "!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Notificacao notificacao = new Notificacao();
        notificacao.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Notificacao> not = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Notificacao notificacao = ds.getValue(Notificacao.class);
                    not.add(notificacao);

                }

                adapter = new NotificacoesAdapter(not, context);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerMensagens.setLayoutManager(layoutManager);
                recyclerMensagens.setHasFixedSize(true);
                recyclerMensagens.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
                recyclerMensagens.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void abrirTela(Class tela){
        Intent intent = new Intent(this, tela);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.caixaMensagens) {
            // Handle the camera action
        } else if (id == R.id.proximosEventos) {

        } else if (id == R.id.calendarioAcademico) {

        } else if (id == R.id.forumPais) {

        } else if (id == R.id.boletim) {

        } else if (id == R.id.frequencia) {

        } else if (id == R.id.documentos) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
