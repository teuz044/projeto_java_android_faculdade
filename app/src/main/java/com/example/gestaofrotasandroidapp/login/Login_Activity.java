package com.example.gestaofrotasandroidapp.login;

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
import com.example.gestaofrotasandroidapp.cadastro.Cadastro_Activity;
import com.example.gestaofrotasandroidapp.visualizarTarefas.MainActivity;
import com.example.gestaofrotasandroidapp.DataBaseHelper;

public class Login_Activity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        dbHelper = new DataBaseHelper(this);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os valores de usuário e senha
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Verificar se o usuário e a senha estão corretos
                if (loginUsuario(username, password)) {
                    // Se o login for bem-sucedido, vá para a MainActivity
                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                    finish();
                } else {
                    // Se o login falhar, exibir uma mensagem de erro
                    Toast.makeText(Login_Activity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vá para a tela de cadastro
                startActivity(new Intent(Login_Activity.this, Cadastro_Activity.class));
            }
        });
    }

    private boolean loginUsuario(String username, String password) {
        // Abrir o banco de dados em modo de leitura
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta SQL para verificar se o usuário e a senha estão corretos
        String query = "SELECT * FROM " + DataBaseHelper.TABLE_USUARIOS + " WHERE " +
                DataBaseHelper.COLUMN_USUARIO + " = ? AND " +
                DataBaseHelper.COLUMN_SENHA + " = ?";

        // Executar a consulta SQL
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        // Verificar se há algum resultado retornado pela consulta
        boolean loginSucesso = cursor.getCount() > 0;

        // Fechar o cursor e o banco de dados
        cursor.close();
        db.close();

        // Retornar true se o login for bem-sucedido, caso contrário, false
        return loginSucesso;
    }
}
