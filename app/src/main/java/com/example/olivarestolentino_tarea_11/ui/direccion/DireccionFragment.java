package com.example.olivarestolentino_tarea_11.ui.direccion;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.adapter.DireccionListAdapter;
import com.example.olivarestolentino_tarea_11.db.DBHelper;
import com.example.olivarestolentino_tarea_11.modelo.Direccion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DireccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DireccionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DireccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DireccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DireccionFragment newInstance(String param1, String param2) {
        DireccionFragment fragment = new DireccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
















    //my code------------------------------------------------------------
    int LAUNCH_CLIENTE_ACTIVITY = 1;


    private ListView lvDirecciones;
    private DBHelper dbHelper;


    DireccionListAdapter adapterDireccion;
    Direccion currentDireccion = new Direccion();

    Dialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_direccion, container, false);

        dbHelper = new DBHelper(getActivity().getBaseContext());

        lvDirecciones = root.findViewById(R.id.lv_direcciones);

        ListarDirecciones();






        //boton nuevo direccion
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_nuevaDireccion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Go get some sleep", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent i = new Intent(getContext(), DireccionFormActivity.class);
                startActivityForResult(i, LAUNCH_CLIENTE_ACTIVITY);
            }
        });
        
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDirecciones.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Direccion direccion = (Direccion) lvDirecciones.getItemAtPosition(position);
                openDialog(direccion);
                return false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_CLIENTE_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
                ListarDirecciones();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Snackbar.make(getView(), "Ningun direccion alterado.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }


    private  void openDialog(Direccion direccion){
        Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
        View custom_dialog = getActivity().getLayoutInflater().inflate(R.layout.cliente_acciones_dialog, null);
        dialog.setContentView(custom_dialog);

        Button btnEliminar = custom_dialog.findViewById(R.id.btn_eliminarCliente);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = dbHelper.EliminarDireccion(direccion.getIdDireccion());
                if (r > 0){
                    Toast.makeText(getContext(), "Se eliminó la direccion: " + direccion.getCalle(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                ListarDirecciones();
            }
        });

        Button btnEditar = custom_dialog.findViewById(R.id.btn_editarCliente);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DireccionFormActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("obj", direccion );
                intent.putExtras(bundle);
                startActivityForResult(intent, LAUNCH_CLIENTE_ACTIVITY);
                dialog.dismiss();
                // Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void ListarDirecciones() {
        adapterDireccion = new DireccionListAdapter(getActivity().getBaseContext(), dbHelper.GetDireccionArrayList());
        lvDirecciones.setAdapter(adapterDireccion);
    }
}