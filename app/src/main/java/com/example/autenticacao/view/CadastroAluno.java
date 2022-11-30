package com.example.autenticacao.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autenticacao.R;
import com.example.autenticacao.view.models.Aluno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CadastroAluno extends AppCompatActivity {

    private EditText RA;
    private EditText Nome;
    private EditText Endereco;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private DatabaseReference refAluno;
    private ListView Alunos;
    private List<Aluno> listALunos = new ArrayList<Aluno>();
    private ArrayAdapter<Aluno> arrayAdapterAluno;
    private Aluno AlunoSelecionado;
    private final String CHILD = "aluno";
    private FirebaseAuth firebaseAuth;
    private Button btnSalvar, btnAtualizar, btnDeletar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        inicializarComponentes();
        inicializarFirebase();
        DatabaseReference refAluno = reference.child("aluno");
        listarAlunos();
        cliqueList();

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AlunoSelecionado == null) {
                    Toast.makeText(CadastroAluno.this, "Selecione um aluno para deletar", Toast.LENGTH_SHORT).show();
                } else {
                    deleteAluno();
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificarNulo() == true) {
                    Toast.makeText(CadastroAluno.this, "Preecha todos os campos para salvar", Toast.LENGTH_SHORT).show();
                } else {
                    alunoSalvar();
                }
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (AlunoSelecionado == null) {
                    Toast.makeText(CadastroAluno.this, "Selecione um aluno para atualizar", Toast.LENGTH_SHORT).show();
                } else {
                    updateAluno();
                }
            }
        });

    }

    private void cliqueList() {
        Alunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlunoSelecionado = (Aluno) adapterView.getItemAtPosition(i);
                RA.setText(AlunoSelecionado.getRA());
                Nome.setText(AlunoSelecionado.getNome());
                Endereco.setText(AlunoSelecionado.getEndereco());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void listarAlunos() {
        reference.child("aluno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listALunos.clear();
                for (DataSnapshot obj : snapshot.getChildren()) {
                    Aluno a = obj.getValue(Aluno.class);
                    listALunos.add(a);
                }
                arrayAdapterAluno = new ArrayAdapter<Aluno>(CadastroAluno.this,
                        android.R.layout.simple_list_item_1,
                        listALunos);
                Alunos.setAdapter(arrayAdapterAluno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int op = item.getItemId();
        if (op == R.id.menu_delete) {
            kill();
        } else {
            alert("Opcão invalida");
        }
        return true;
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        reference = firebaseDatabase.getReference();
        refAluno = reference.child("aluno");
    }

    private void inicializarComponentes() {
        RA = findViewById(R.id.edtRa);
        Nome = findViewById(R.id.edtNome);
        Endereco = findViewById(R.id.edtEndereco);
        Alunos = findViewById(R.id.lstAlunos);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnDeletar = findViewById(R.id.btnDelete);

    }

    private void limparCampos() {
        RA.setText("");
        Nome.setText("");
        Endereco.setText("");
    }

    public void OnClickSalvar(View v) {
        if (RA.getText().toString().isEmpty() && Nome.getText().toString().isEmpty() && Endereco.getText().toString().isEmpty()) {
            Toast.makeText(this, "Prencha todos os campos para poder salvar!", Toast.LENGTH_SHORT).show();
            return;
        }
        Aluno aluno = new Aluno();
        aluno.setRA(RA.getText().toString().trim());
        aluno.setNome(Nome.getText().toString().trim());
        aluno.setEndereco(Endereco.getText().toString().trim());
        reference.child(CHILD).child(aluno.getUid()).setValue(aluno);
        Toast.makeText(this, "Aluno cadastrado com sucesso!!!", Toast.LENGTH_SHORT).show();
        limparCampos();
    }

    private void alunoSalvar() {
        if (RA.getText().toString().isEmpty() && Nome.getText().toString().isEmpty() && Endereco.getText().toString().isEmpty()) {
            Toast.makeText(this, "Prencha todos os campos para poder salvar!", Toast.LENGTH_SHORT).show();
            return;
        }
        Aluno aluno = new Aluno();
        aluno.setRA(RA.getText().toString().trim());
        aluno.setNome(Nome.getText().toString().trim());
        aluno.setEndereco(Endereco.getText().toString().trim());
        reference.child(CHILD).child(aluno.getUid()).setValue(aluno);
        alert(aluno.getNome() + " foi salvo com sucesso");
        limparCampos();
    }

    private void deleteAluno() {
        Aluno aluno = new Aluno();
        aluno.setUid(AlunoSelecionado.getUid());
        reference.child(CHILD).child(aluno.getUid()).removeValue();
        alert(aluno.getNome() + "foi deletado com sucesso");
        limparCampos();
    }

    private void kill() {
        firebaseAuth.signOut();
        startActivity(new Intent(CadastroAluno.this, LoginActivity.class));
    }

    private boolean verificarNulo() {

        if (RA.getText().toString().isEmpty() || Nome.getText().toString().isEmpty() || Endereco.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void updateAluno() {
        Aluno aluno = new Aluno();
        aluno.setUid(AlunoSelecionado.getUid());
        aluno.setNome(Nome.getText().toString().trim());
        aluno.setRA(RA.getText().toString().trim());
        aluno.setEndereco(Endereco.getText().toString().trim());
        reference.child(CHILD).child(aluno.getUid()).setValue(aluno);
        alert(aluno.getNome() + " foi alterado com sucesso");
        limparCampos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //startActivity(new Intent(CadastroAluno.this, LoginActivity.class));
    }

    private void alert(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
