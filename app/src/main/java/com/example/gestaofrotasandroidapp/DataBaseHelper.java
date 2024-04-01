package com.example.gestaofrotasandroidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carros.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela de veículos
    public static final String TABLE_CARROS = "carros";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_MARCA = "marca";
    public static final String COLUMN_PLACA = "placa";

    // Comando SQL para criar a tabela de veículos
    private static final String CREATE_TABLE_CARROS = "CREATE TABLE " +
            TABLE_CARROS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOME + " TEXT, " +
            COLUMN_MARCA + " TEXT, " +
            COLUMN_PLACA + " TEXT" +
            ")";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar a tabela de veículos
        db.execSQL(CREATE_TABLE_CARROS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se houver atualização do banco de dados, você pode implementar a lógica aqui
    }
}
