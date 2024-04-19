package com.example.gestaofrotasandroidapp.cadastro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.login.Login_Activity;
import com.example.gestaofrotasandroidapp.DataBaseHelper;

public class Cadastro_Activity extends AppCompatActivity {

    private EditText editTextNome, editTextUsuario, editTextSenha;
    private Button buttonIrParaLogin, buttonCadastrar;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_layout);

        dbHelper = new DataBaseHelper(this);

        // Inicialize os componentes da interface
        editTextNome = findViewById(R.id.editTextNome);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonIrParaLogin = findViewById(R.id.buttonIrParaLogin);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        // Defina o clique do botão "Ir para o Login"
        buttonIrParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abra a atividade de login
                Intent intent = new Intent(Cadastro_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        // Defina o clique do botão "Cadastrar"
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para tratar o cadastro aqui
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        // Obter os valores dos campos de entrada
        String nome = editTextNome.getText().toString().trim();
        String usuario = editTextUsuario.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        // Verificar se os campos estão vazios
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
            // Se algum campo estiver vazio, exibir uma mensagem de aviso
            Toast.makeText(Cadastro_Activity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        } else {
            // Consultar o banco de dados para verificar se já existe um usuário com as mesmas credenciais
            if (verificarUsuarioExistente(usuario)) {
                // Se o usuário já existir, exibir uma mensagem de aviso
                Toast.makeText(Cadastro_Activity.this, "Usuário já existe, por favor, escolha outro nome de usuário", Toast.LENGTH_SHORT).show();
            } else {
                // Se o usuário não existir, inserir o novo registro na tabela de usuários
                inserirNovoUsuario(nome, usuario, senha);

                // Redirecionar para a tela de login
                Intent intent = new Intent(Cadastro_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish(); // Encerrar a atividade atual para evitar que o usuário volte para a tela de cadastro
            }
        }
    }

    private boolean verificarUsuarioExistente(String usuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DataBaseHelper.TABLE_USUARIOS + " WHERE " + DataBaseHelper.COLUMN_USUARIO + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{usuario});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }


    private void inserirNovoUsuario(String nome, String usuario, String senha) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_NOME, nome);
        values.put(DataBaseHelper.COLUMN_USUARIO, usuario);
        values.put(DataBaseHelper.COLUMN_SENHA, senha);
        db.insert(DataBaseHelper.TABLE_USUARIOS, null, values);
        db.close();
    }
}
