package com.example.olivarestolentino_tarea_11.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.db.DBHelper;
import com.example.olivarestolentino_tarea_11.modelo.Cliente;
import com.example.olivarestolentino_tarea_11.modelo.Direccion;

import java.util.ArrayList;
import java.util.List;

public class DireccionListAdapter extends ArrayAdapter<Direccion> {

    private  static final String TAG = "ItemListAdapter";
    private Context mContext;
    DBHelper dbHelper;

    TextView tvDireccion, tvNombre;

    public DireccionListAdapter(@NonNull Context context, @NonNull ArrayList<Direccion> objects) {
        super(context, R.layout.direccion_list_adapter, objects);
        mContext = context;
        dbHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.direccion_list_adapter, null);

        tvDireccion = convertView.findViewById(R.id.tv_direccion);
        tvNombre = convertView.findViewById(R.id.tv_clienteNombre2);

        String text = getItem(position).getCalle() +" "+ getItem(position).getNumero() + " - "+ getItem(position).getComuna() + ", " + getItem(position).getCiudad() ;
        tvDireccion.setText(text);


        //get cliente object
        Cliente cli = dbHelper.GetClientePorId(getItem(position).getIdCliente());
        if(cli != null)
            tvNombre.setText(cli.getNombre());
        else
            tvNombre.setText("-");


        return convertView;
    }
}
