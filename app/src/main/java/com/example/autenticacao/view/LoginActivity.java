package com.example.autenticacao.view;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autenticacao.R;
import com.example.autenticacao.view.models.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private Button btnCadastrar;
    private Button btnSair;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        btnLogar = findViewById(R.id.btnLogLogar);
        btnCadastrar = findViewById(R.id.btnLogCadastrar);
        edtSenha = findViewById(R.id.edtLogSenha);
        edtEmail = findViewById(R.id.edtLogEmail);
        btnSair = findViewById(R.id.btnLogSair);
        firebaseAuth.signOut();
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificarNulo() == true) {
                    return;
                }
                buscarDados();
                logar();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDados();
                criarLogin();
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                if (firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(LoginActivity.this, "Usuario logado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Deslogando, encerrando", Toast.LENGTH_SHORT).show();
                    limparCampos();
                    finish();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
        } else {
            Toast.makeText(this, "Usuario não logado, Por favor autentique-se!!!", Toast.LENGTH_SHORT).show();
            firebaseAuth.getInstance();
            limparCampos();
        }
    }

    private void logar() {
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, CadastroAluno.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha ao Autenticar!.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    private boolean verificarNulo() {
        if (edtEmail.getText().toString().isEmpty() || edtSenha.getText().toString().isEmpty()) {
            Toast.makeText(this, "Prencha todos os campos para poder logar!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private void updateUI(FirebaseUser user) {

    }

    private void buscarDados() {
        usuario = new Usuario();
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());
    }

    private void criarLogin() {
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, CadastroAluno.class));
                            Toast.makeText(LoginActivity.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Não foi possivel criar login, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void limparCampos() {
        edtSenha.setText("");
        edtEmail.setText("");
    }
}