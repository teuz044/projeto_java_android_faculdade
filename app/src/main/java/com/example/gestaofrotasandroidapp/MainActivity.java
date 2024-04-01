package com.example.gestaofrotasandroidapp;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ListView carListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> carList;

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

        // Inicializar o adaptador para a ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carList);
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
                // Ler dados de cada linha e adicionar à lista de carros
                @SuppressLint("Range") String carName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NOME));
                carList.add(carName);
            } while (cursor.moveToNext());
        }

        // Fechar o cursor e o banco de dados
        cursor.close();
        db.close();

        // Notificar o adaptador sobre a mudança na lista
        adapter.notifyDataSetChanged();
    }
}
