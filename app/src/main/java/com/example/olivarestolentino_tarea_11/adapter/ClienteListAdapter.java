package com.example.olivarestolentino_tarea_11.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.modelo.Cliente;

import java.util.ArrayList;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    private  static final String TAG = "ItemListAdapter";
    private Context mContext;

    TextView tvId, tvNombre;


    public ClienteListAdapter(@NonNull Context context, @NonNull ArrayList<Cliente> objects) {
        super(context, R.layout.cliente_list_adapter, objects);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        //create the item object  with the information
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.cliente_list_adapter, null);

        tvId = convertView.findViewById(R.id.tv_clienteID);
        tvNombre = convertView.findViewById(R.id.tv_clienteNombre);

        tvId.setText(getItem(position).getIdCliente() + "");
        tvNombre.setText(getItem(position).getNombre());


        return convertView;
    }

    @Nullable
    @Override
    public Cliente getItem(int position) {
        return super.getItem(position);
    }
}
