package com.example.olivarestolentino_tarea_11.ui.cliente;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.adapter.ClienteListAdapter;
import com.example.olivarestolentino_tarea_11.db.DBHelper;
import com.example.olivarestolentino_tarea_11.modelo.*;
import com.example.olivarestolentino_tarea_11.ui.cliente.ClienteFormActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ClienteFragment extends Fragment {


    int LAUNCH_CLIENTE_ACTIVITY = 1;


    private ListView lvClientes;
    private DBHelper dbHelper;


    ClienteListAdapter adapterCliente;
    Cliente currentCliente = new Cliente();

    Dialog dialog;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cliente_fragment, container, false);

        dbHelper = new DBHelper(getActivity().getBaseContext());

        lvClientes = root.findViewById(R.id.lv_clientes);

        ListarClietes();






        //boton nuevo cliente
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_nuevoCliente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Go get some sleep", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent i = new Intent(getContext(), ClienteFormActivity.class);
                startActivityForResult(i, LAUNCH_CLIENTE_ACTIVITY);
            }
        });




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cli = (Cliente) lvClientes.getItemAtPosition(position);
                openDialog(cli);
                return false;
            }
        });
    }

    private void ListarClietes() {
        adapterCliente = new ClienteListAdapter(getActivity().getBaseContext(), dbHelper.GetClienteArrayList());
        lvClientes.setAdapter(adapterCliente);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_CLIENTE_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
                ListarClietes();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Snackbar.make(getView(), "Ningun cliente alterado.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }


    private  void openDialog(Cliente cliente){
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
        View custom_dialog = getActivity().getLayoutInflater().inflate(R.layout.cliente_acciones_dialog, null);
        dialog.setContentView(custom_dialog);

        Button btnEliminar = custom_dialog.findViewById(R.id.btn_eliminarCliente);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = dbHelper.EliminarCliente(cliente.getIdCliente());
                if (r > 0){
                    Toast.makeText(getContext(), "Se eliminó el cliente: " + cliente.getNombre(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                ListarClietes();
            }
        });

        Button btnEditar = custom_dialog.findViewById(R.id.btn_editarCliente);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClienteFormActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("obj", cliente );
                intent.putExtras(bundle);
                startActivityForResult(intent, LAUNCH_CLIENTE_ACTIVITY);
                dialog.dismiss();
               // Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}