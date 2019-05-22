package com.junior.cmap.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.junior.cmap.R;
import com.junior.cmap.fragments.register.PersonalDataFragment;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i("click","entrou na register");
        getSupportActionBar().setTitle("Registrar-se");
        Fragment step_1 = PersonalDataFragment.newInstance();
        openFragment(step_1);
    }

    public void openFragment(Fragment fragment) {
        //Declaração e inicialização da transação
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //Substitui o fragment colocado dentro do container
        transaction.replace(R.id.container, fragment);
        //Adiciona a transação na pilha
        transaction.addToBackStack(null);
        //Fecha a transação
        transaction.commit();
    }
}
