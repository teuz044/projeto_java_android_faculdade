package com.example.gestaofrotasandroidapp.visualizarTarefas;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaofrotasandroidapp.DataBaseHelper;
import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.adicionarTarefas.adicionarNovaTarefa;
import com.example.gestaofrotasandroidapp.login.Login_Activity;
import com.example.gestaofrotasandroidapp.visualizarTarefas.models.Tarefa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView taskListView;
    private TarefaAdapter adapter;
    private ArrayList<Tarefa> taskList;

    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Gestão de tarefas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Habilitar o botão de voltar

        addButton = findViewById(R.id.buttonAddCar);
        taskListView = findViewById(R.id.listViewCars);

        // Defina as opções para o Spinner (status da tarefa)
        String[] statusOptions = {"Fazendo", "Concluído"};

        taskList = new ArrayList<>();
        adapter = new TarefaAdapter(this, R.layout.lst_item_tarefa, taskList);
        taskListView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, adicionarNovaTarefa.class);
                startActivity(intent);
            }
        });

        loadTasks();

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa task = taskList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Editar Tarefa");

                View editTaskView = getLayoutInflater().inflate(R.layout.edit_tarefa_layout, null);
                builder.setView(editTaskView);

                EditText editTextDescription = editTaskView.findViewById(R.id.editTextDescription);
                Spinner spinnerPriority = editTaskView.findViewById(R.id.editSpinnerPriority); // Alterado

                // Defina as opções para o Spinner de prioridade
                String[] priorityOptions = {"1", "2", "3"};
                ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, priorityOptions);
                priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPriority.setAdapter(priorityAdapter);

                editTextDescription.setText(task.getDescricao());
                spinnerPriority.setSelection(task.getPrioridade() - 1); // Subtraia 1 porque os índices do Spinner começam em 0

                builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newDescription = editTextDescription.getText().toString().trim();
                        int newPriority = spinnerPriority.getSelectedItemPosition() + 1; // +1 porque os índices do Spinner começam em 0

                        updateTask(task.getId(), newDescription, newPriority, task.getStatus());
                        loadTasks();
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.setNeutralButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask(task.getId());
                        loadTasks();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verificar se o item de menu pressionado é o botão de voltar
        if (item.getItemId() == android.R.id.home) {
            // Iniciar a atividade de login
            startActivity(new Intent(MainActivity.this, Login_Activity.class));
            finish(); // Encerrar a atividade atual
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTasks() {
        taskList.clear();

        dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_TAREFAS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long taskId = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                @SuppressLint("Range") String taskDescription = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_DESCRICAO));
                @SuppressLint("Range") int taskPriority = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRIORIDADE));
                @SuppressLint("Range") int taskStatus = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_STATUS));

                Tarefa task = new Tarefa(taskId, taskDescription, taskPriority, taskStatus);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
    }

    private void updateTask(long taskId, String newDescription, int newPriority, int newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_DESCRICAO, newDescription);
        values.put(DataBaseHelper.COLUMN_PRIORIDADE, newPriority);
        values.put(DataBaseHelper.COLUMN_STATUS, newStatus);

        db.update(DataBaseHelper.TABLE_TAREFAS, values, DataBaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(taskId)});

        db.close();

        Toast.makeText(this, "Tarefa atualizada", Toast.LENGTH_SHORT).show();
    }

    private void deleteTask(long taskId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBaseHelper.TABLE_TAREFAS, DataBaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();

        loadTasks();

        Toast.makeText(this, "Tarefa deletada", Toast.LENGTH_SHORT).show();
    }
}
