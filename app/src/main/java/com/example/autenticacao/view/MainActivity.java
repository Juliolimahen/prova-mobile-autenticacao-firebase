package com.example.autenticacao.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.autenticacao.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogar;
    private Button btnCadastrar;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCadastrar = findViewById(R.id.btnMainCadastrar);
        btnLogar = findViewById(R.id.btnMainLogar);


        btnSair = findViewById(R.id.btnMainSair);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaLogar();
            }

            private void telaLogar() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaCadastar();
            }

            private void telaCadastar() {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });
    }
}