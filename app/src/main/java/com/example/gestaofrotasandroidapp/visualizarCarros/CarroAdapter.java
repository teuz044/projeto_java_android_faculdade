package com.example.gestaofrotasandroidapp.visualizarCarros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gestaofrotasandroidapp.R;
import com.example.gestaofrotasandroidapp.visualizarCarros.models.Carro;

import java.util.List;

public class CarroAdapter extends ArrayAdapter<Carro> {

    private Context mContext;
    private int mResource;

    public CarroAdapter(Context context, int resource, List<Carro> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Carro currentCarro = getItem(position);

        TextView textViewCarName = listItem.findViewById(R.id.textViewCarName);
        textViewCarName.setText(currentCarro.getNome());

        TextView textViewSubtitle = listItem.findViewById(R.id.textViewSubtitle);
        textViewSubtitle.setText(currentCarro.getPlaca()); // Exibindo a placa como subtítulo

        return listItem;
    }
}
