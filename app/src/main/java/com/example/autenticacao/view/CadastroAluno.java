package com.example.autenticacao.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autenticacao.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private final String CHILD = "Aluno";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        inicializarComponentes();
        inicializarFirebase();
        DatabaseReference refAluno = reference.child("aluno");
        listarAlunos();
        Alunos.setCacheColorHint(R.color.white);
        /*Alunos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AlunoSelecionado= (Aluno)adapterView.getItemAtPosition(i);
                RA.setText(AlunoSelecionado.getRA());
                Nome.setText(AlunoSelecionado.getNome());
                Endereco.setText(AlunoSelecionado.getEndereco());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
        //DatabaseReference refAluno = reference.child("aluno");
        //Aluno a = new Aluno("0590/19", "Julio", "Ouro Verde");
        //refAluno.child("001").setValue(a);
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
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idEscolhido = item.getItemId();

        /*switch (idEscolhido){
            case R.id.menu_new:
                newPessoa();
                break;
            case R.id.menu_update:
                   updatePessoa();
                break;
            case R.id.menu_delete:
                deletePessoa();
                break;
            default:
                alert("Opção não identificada");
                break;
        }*/

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
        Aluno a = new Aluno(
                RA.getText().toString(),
                Nome.getText().toString(),
                Endereco.getText().toString()
        );
        refAluno.push().setValue(a);
        Toast.makeText(this, "Aluno cadastrado com sucesso!!!", Toast.LENGTH_SHORT).show();
        limparCampos();
    }
    private void deleteAleuno() {
        Aluno aluno = new Aluno();
        aluno.setId(AlunoSelecionado.getId());
        reference.child(CHILD).child(aluno.getId()).removeValue();
        alert("objeto deletado com sucesso");
        limparCampos();
    }

    private void updatePessoa() {
        Aluno aluno = new Aluno ();
        aluno.setId(AlunoSelecionado.getId());
        aluno.setNome(Nome.getText().toString().trim());
        aluno.setRA(RA.getText().toString().trim());
        reference.child(CHILD).child(aluno.getId()).setValue(aluno);
        alert(aluno.getNome() + " foi alterado com sucesso");
        limparCampos();
    }

    private void alert(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
