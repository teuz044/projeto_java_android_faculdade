package com.example.gestaofrotasandroidapp.visualizarTarefas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.visualizarTarefas.models.Tarefa;

import java.util.List;

public class TarefaAdapter extends ArrayAdapter<Tarefa> {

    private Context mContext;
    private int mResource;

    public TarefaAdapter(Context context, int resource, List<Tarefa> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Tarefa currentTarefa = getItem(position);

        TextView textViewTaskDescription = listItem.findViewById(R.id.textViewTaskDescription);
        textViewTaskDescription.setText(currentTarefa.getDescricao());

        TextView textViewSubtitle = listItem.findViewById(R.id.textViewSubtitle);
        textViewSubtitle.setText("Prioridade: " + currentTarefa.getPrioridade());

        // Definindo a cor do texto com base na prioridade
        int priority = currentTarefa.getPrioridade();
        int colorResId;

        switch (priority) {
            case 1:
                colorResId = R.color.green; // Defina a cor verde
                break;
            case 2:
                colorResId = R.color.orange; // Defina a cor laranja
                break;
            case 3:
            default:
                colorResId = R.color.red; // Defina a cor vermelha
                break;
        }

        textViewSubtitle.setTextColor(mContext.getResources().getColor(colorResId));

        return listItem;
    }


}
