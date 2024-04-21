package com.example.gestaofrotasandroidapp.tarefasRealizadas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestaofrotasandroidapp.DataBaseHelper;
import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.visualizarTarefas.MainActivity;
import com.example.gestaofrotasandroidapp.visualizarTarefas.models.Tarefa;
import java.util.ArrayList;

public class Tarefas_Realizadas_Activity extends AppCompatActivity {

    private ListView listViewTarefasRealizadas;
    private ArrayList<Tarefa> tarefasRealizadasList;
    private Tarefas_Realizadas_Adapter adapter; // Alteração aqui

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Tarefas Realizadas");
        setContentView(R.layout.tarefas_realizadas_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewTarefasRealizadas = findViewById(R.id.listViewTarefasRealizadas);
        tarefasRealizadasList = new ArrayList<>();
        adapter = new Tarefas_Realizadas_Adapter(this, tarefasRealizadasList); // Alteração aqui
        listViewTarefasRealizadas.setAdapter(adapter);
        loadTarefasRealizadas();
    }

    private void loadTarefasRealizadas() {
        tarefasRealizadasList.clear();
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_TAREFAS +
                " WHERE " + DataBaseHelper.COLUMN_STATUS + " = 1", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") long taskId = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                @SuppressLint("Range") String taskDescription = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_DESCRICAO));
                @SuppressLint("Range") int taskPriority = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRIORIDADE));
                @SuppressLint("Range") int taskStatus = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_STATUS));
                Tarefa tarefa = new Tarefa(taskId, taskDescription, taskPriority, taskStatus);
                tarefasRealizadasList.add(tarefa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(Tarefas_Realizadas_Activity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
