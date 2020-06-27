package com.example.pojectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
 ImageView image;
 EditText username;
 EditText password;
 String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.image);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);



    }
 //Créer un compte
    public void createcompte(View view) {
        user=username.getText().toString();
        pass=password.getText().toString();

        if(user.isEmpty()){
            Toast.makeText(MainActivity.this,"Entrer une @ email",Toast.LENGTH_SHORT).show();
            return;

        }
        if(pass.isEmpty()){
            Toast.makeText(MainActivity.this,"Entrer un password",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Compte créer avec succés, "+"Login pour continuer",Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(MainActivity.this,"Echec:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
//login
    public void login(View view) {
        user=username.getText().toString();
        pass=password.getText().toString();
        if(user.isEmpty()){
            Toast.makeText(MainActivity.this,"Entrer une @ email",Toast.LENGTH_SHORT).show();
            return;

        }
        if(pass.isEmpty()){
            Toast.makeText(MainActivity.this,"Entrer un password",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                    finish();

                }else
                    Toast.makeText(MainActivity.this,"Echec:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

            }

        });
    }
//rénitialiser le mot de passe
    public void resetpassword(View view) {
        user=username.getText().toString();
        pass=password.getText().toString();
        if(user.isEmpty()){
            Toast.makeText(MainActivity.this,"Entrer une @ email",Toast.LENGTH_SHORT).show();
            return;

        }
        FirebaseAuth auth= FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Reset email envoyé avec succés",Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(MainActivity.this,"Echec:"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();


            }
        });
    }
}
