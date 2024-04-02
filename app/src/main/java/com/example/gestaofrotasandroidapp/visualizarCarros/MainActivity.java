package com.example.gestaofrotasandroidapp.visualizarCarros;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestaofrotasandroidapp.DataBaseHelper;
import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.adicionarNovoCarro;
import com.example.gestaofrotasandroidapp.visualizarCarros.models.Carro;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView carListView;
    private CarroAdapter adapter;
    private ArrayList<Carro> carList; // Alterado para ArrayList<Carro>

    // Criar uma instância do DatabaseHelper
    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Gestão de frotas");

        // Referenciar os componentes do layout XML
        addButton = findViewById(R.id.buttonAddCar);
        carListView = findViewById(R.id.listViewCars);

        // Inicializar a lista de carros
        carList = new ArrayList<>();

        // Inicializar o adaptador para a ListView com os objetos Carro
        adapter = new CarroAdapter(this, R.layout.lst_item_car, carList);
        carListView.setAdapter(adapter);

        // Configurar o listener de clique para o botão "Adicionar Carro"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirecionar para a tela de formulário de adição de carro
                Intent intent = new Intent(MainActivity.this, adicionarNovoCarro.class);
                startActivity(intent);
            }
        });

        // Carregar carros da tabela quando a atividade for criada
        loadCars();

        // Configurar o listener de clique para o botão de exclusão de carro
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Deletar o carro da lista e do banco de dados
                Carro car = carList.get(position); // Obter o carro da lista
                deleteCar(car.getId()); // Passar o ID do carro para o método deleteCar
            }
        });
    }

    // Método para carregar carros da tabela e atualizar o ListView
    private void loadCars() {
        // Limpar a lista de carros antes de carregar novamente
        carList.clear();

        // Criar uma instância do DatabaseHelper
        dbHelper = new DataBaseHelper(this);

        // Obter uma instância do banco de dados em modo de leitura
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta para selecionar todos os carros da tabela
        Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_CARROS, null);

        // Verificar se há linhas na tabela
        if (cursor.moveToFirst()) {
            do {
                // Ler dados de cada linha e criar um objeto Carro
                @SuppressLint("Range") long carId = cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                @SuppressLint("Range") String carName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NOME));
                @SuppressLint("Range") String carMarca = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_MARCA));
                @SuppressLint("Range") String carPlaca = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PLACA));
                Carro carro = new Carro(carId, carName, carMarca, carPlaca);

                // Adicionar o carro à lista de carros
                carList.add(carro);
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e o banco de dados
        cursor.close();
        db.close();

        // Notificar o adaptador sobre a mudança na lista
        adapter.notifyDataSetChanged();
    }

    // Método para deletar um carro da lista e do banco de dados
    private void deleteCar(long carId) {
        // Deletar o carro do banco de dados usando o ID
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DataBaseHelper.TABLE_CARROS, DataBaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
        db.close();

        // Atualizar a lista de carros após a exclusão
        loadCars();

        // Exibir uma mensagem de confirmação
        Toast.makeText(this, "Carro deletado", Toast.LENGTH_SHORT).show();
    }
}