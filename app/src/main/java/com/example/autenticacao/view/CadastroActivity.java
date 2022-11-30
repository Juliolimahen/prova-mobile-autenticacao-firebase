package com.example.autenticacao.view;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autenticacao.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnCadastrar;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtSenha = findViewById(R.id.edtCadSenha);
        edtNome = findViewById(R.id.edtCadNome);
        edtEmail = findViewById(R.id.edtCadEmail);
        btnCadastrar = findViewById(R.id.btmCadCadastro);

        firebaseAuth = FirebaseAuth.getInstance();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDados();
                criarLogin();
            }
        });
    }

    private void criarLogin() {
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(CadastroActivity.this, PrincipalActivity.class));
                            Toast.makeText(CadastroActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CadastroActivity.this, "Não foi possivel criar login, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void buscarDados() {
        if (edtNome.getText().toString() == "" || edtEmail.getText().toString() == "" || edtSenha.getText().toString() == "") {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else {
            usuario = new Usuario();
            usuario.setNome(edtNome.getText().toString());
            usuario.setEmail(edtEmail.getText().toString());
            usuario.setSenha(edtSenha.getText().toString());
        }
    }
}