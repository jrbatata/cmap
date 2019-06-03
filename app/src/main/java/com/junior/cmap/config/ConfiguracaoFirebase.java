package com.junior.cmap.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    /*private static DatabaseReference reference;
    private static StorageReference storage;*/
    private static FirebaseAuth auth;
    private static DatabaseReference reference;

    public static DatabaseReference getFirebase(){
        //Verifica se a referência é nula
        if (reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        //Retorna a referência ao banco de dados
        return reference;
    }

    public static FirebaseAuth getFireBaseAuth(){
        //Verifica se a referência à Autenticação é nula
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }
        //Retorna a referência à Autenticação
        return auth;
    }

    /*public static StorageReference getStorage(){
        //Verifica se o storage é nulo
        if(storage == null){
            storage = FirebaseStorage.getInstance().getReference();
        }
        //Retorna a referência do Storage
        return storage;
    }*/

}
