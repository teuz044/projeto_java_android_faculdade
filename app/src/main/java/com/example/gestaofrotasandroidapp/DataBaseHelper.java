package com.example.gestaofrotasandroidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tarefas.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de tarefas
    public static final String TABLE_TAREFAS = "tarefas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_PRIORIDADE = "prioridade";
    public static final String COLUMN_STATUS = "status"; // Novo campo para o status da tarefa

    // Comando SQL para criar a tabela de tarefas
    private static final String CREATE_TABLE_TAREFAS = "CREATE TABLE " +
            TABLE_TAREFAS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DESCRICAO + " TEXT, " +
            COLUMN_PRIORIDADE + " INTEGER, " +
            COLUMN_STATUS + " INTEGER DEFAULT 0" + // Valor padrão para o status é 0 (não realizada)
            ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar a tabela de tarefas
        db.execSQL(CREATE_TABLE_TAREFAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se houver atualização do banco de dados, você pode implementar a lógica aqui
    }
}
