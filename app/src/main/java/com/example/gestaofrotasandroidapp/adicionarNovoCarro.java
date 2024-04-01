package com.example.gestaofrotasandroidapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class adicionarNovoCarro extends AppCompatActivity {

    private Button backButton;
    private Button addVeiculoButton;
    private EditText editTextCarName;
    private EditText editTextCarBrand;
    private EditText editTextCarPlate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_novo_carro);
        getSupportActionBar().setTitle("Adicionar novo veículo");

        backButton = findViewById(R.id.buttonBackToViewCars);
        addVeiculoButton = findViewById(R.id.buttonAddCar);
        editTextCarName = findViewById(R.id.editTextCarName);
        editTextCarBrand = findViewById(R.id.editTextCarBrand);
        editTextCarPlate = findViewById(R.id.editTextCarPlate);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a tela de visualização de carros
                Intent intent = new Intent(adicionarNovoCarro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addVeiculoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obter os valores dos campos de texto
                String carName = editTextCarName.getText().toString();
                String carBrand = editTextCarBrand.getText().toString();
                String carPlate = editTextCarPlate.getText().toString();

                // Inserir os valores na tabela de veículos
                if (insertCar(carName, carBrand, carPlate)) {
                    // Notificar o usuário sobre o sucesso da operação
                    Toast.makeText(adicionarNovoCarro.this, "Veículo adicionado com sucesso", Toast.LENGTH_SHORT).show();

                    // Limpar os campos de texto
                    editTextCarName.setText("");
                    editTextCarBrand.setText("");
                    editTextCarPlate.setText("");
                } else {
                    // Notificar o usuário sobre a falha da operação
                    Toast.makeText(adicionarNovoCarro.this, "Erro ao adicionar veículo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para inserir um novo carro na tabela de veículos
    private boolean insertCar(String name, String brand, String plate) {
        try {
            // Criar uma instância do DatabaseHelper
            DataBaseHelper dbHelper = new DataBaseHelper(this);

            // Obter uma instância do banco de dados em modo de escrita
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Inserir os valores na tabela de veículos
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLUMN_NOME, name);
            values.put(DataBaseHelper.COLUMN_MARCA, brand);
            values.put(DataBaseHelper.COLUMN_PLACA, plate);
            db.insert(DataBaseHelper.TABLE_CARROS, null, values);

            // Fechar o banco de dados
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
