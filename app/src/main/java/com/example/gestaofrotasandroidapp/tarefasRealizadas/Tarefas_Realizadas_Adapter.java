package com.example.gestaofrotasandroidapp.tarefasRealizadas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.visualizarTarefas.models.Tarefa;
import java.util.ArrayList;

public class Tarefas_Realizadas_Adapter extends ArrayAdapter<Tarefa> {

    private ArrayList<Tarefa> tarefasRealizadasList;
    private Context context;

    public Tarefas_Realizadas_Adapter(Context context, ArrayList<Tarefa> tarefasRealizadasList) {
        super(context, 0, tarefasRealizadasList);
        this.context = context;
        this.tarefasRealizadasList = tarefasRealizadasList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.lst_tarefas_realizadas, parent, false);
        }

        Tarefa tarefa = tarefasRealizadasList.get(position);

        TextView descricaoTextView = listItemView.findViewById(R.id.textViewDescricao);

        descricaoTextView.setText(tarefa.getDescricao());

        return listItemView;
    }
}
