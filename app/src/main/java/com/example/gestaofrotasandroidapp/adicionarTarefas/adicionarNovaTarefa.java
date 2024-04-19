package com.example.gestaofrotasandroidapp.adicionarTarefas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaofrotasandroidapp.DataBaseHelper;
import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.visualizarTarefas.MainActivity;

public class adicionarNovaTarefa extends AppCompatActivity {

    private Button backButton;
    private Button addTaskButton;
    private EditText editTextTaskDescription;
    private Spinner spinnerPriority; // Alterado
    private Spinner spinnerStatus; // Adicionado

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_nova_tarefa);
        getSupportActionBar().setTitle("Adicionar nova tarefa");

        backButton = findViewById(R.id.buttonBackToViewTasks);
        addTaskButton = findViewById(R.id.buttonAddTask);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        spinnerPriority = findViewById(R.id.spinnerPriority); // Alterado
        spinnerStatus = findViewById(R.id.spinnerPriority); // Adicionado

        // Define as opções do spinner de prioridade
        String[] priorityOptions = {"1", "2", "3"};
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorityOptions);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(priorityAdapter);

        // Define as opções do spinner de status
        String[] statusOptions = {"1", "2", "3"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptions);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a tela de visualização de tarefas
                Intent intent = new Intent(adicionarNovaTarefa.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os valores dos campos de texto e dos spinners
                String taskDescription = editTextTaskDescription.getText().toString();
                int taskPriority = Integer.parseInt(spinnerPriority.getSelectedItem().toString());
                int taskStatus = Integer.parseInt(spinnerStatus.getSelectedItem().toString());

                // Inserir os valores na tabela de tarefas
                if (insertTask(taskDescription, taskPriority, taskStatus)) {
                    // Notificar o usuário sobre o sucesso da operação
                    Toast.makeText(adicionarNovaTarefa.this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show();

                    // Limpar os campos de texto
                    editTextTaskDescription.setText("");
                } else {
                    // Notificar o usuário sobre a falha da operação
                    Toast.makeText(adicionarNovaTarefa.this, "Erro ao adicionar tarefa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para inserir uma nova tarefa na tabela de tarefas
    private boolean insertTask(String description, int priority, int status) {
        try {
            // Criar uma instância do DatabaseHelper
            DataBaseHelper dbHelper = new DataBaseHelper(this);

            // Obter uma instância do banco de dados em modo de escrita
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Inserir os valores na tabela de tarefas
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLUMN_DESCRICAO, description);
            values.put(DataBaseHelper.COLUMN_PRIORIDADE, priority);
            values.put(DataBaseHelper.COLUMN_STATUS, status);
            db.insert(DataBaseHelper.TABLE_TAREFAS, null, values);

            // Fechar o banco de dados
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
