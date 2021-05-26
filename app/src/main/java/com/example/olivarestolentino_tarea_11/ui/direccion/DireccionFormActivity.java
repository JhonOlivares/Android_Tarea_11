package com.example.olivarestolentino_tarea_11.ui.direccion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.db.DBHelper;
import com.example.olivarestolentino_tarea_11.modelo.Direccion;

public class DireccionFormActivity extends AppCompatActivity {

    private EditText etNumero, etCalle, etComuna, etCiudad, etClienteID;

    private DBHelper dbHelper;

    Direccion currentDireccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_form);
        dbHelper = new DBHelper(getBaseContext());


        etNumero = findViewById(R.id.et_numeroDireccion);
        etCalle = findViewById(R.id.et_calleDireccion);
        etComuna = findViewById(R.id.et_comunaDireccion);
        etCiudad = findViewById(R.id.et_ciudadDireccion);
        etClienteID = findViewById(R.id.et_clienteIDDireccion);

        Intent intent = getIntent();
        currentDireccion = (Direccion) intent.getSerializableExtra("obj");
        if (currentDireccion != null){
            fillControls();
        }



        findViewById(R.id.btn_guardarDireccion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean iscompleted;
                if (currentDireccion != null && currentDireccion.getIdDireccion() > 0){
                    iscompleted = ActualizarDireccion();
                } else{
                    iscompleted = RegistrarDireccion();
                }
                if (iscompleted){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result","resultado");
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
        
        
    }



    private Boolean RegistrarDireccion(){
        if (!etCalle.getText().toString().isEmpty() && !etClienteID.getText().toString().isEmpty()){
            String numero = etNumero.getText().toString();
            String calle = etCalle.getText().toString();
            String comuna = etComuna.getText().toString();
            String ciudad = etCiudad.getText().toString();
            Integer idCliente = Integer.parseInt(etClienteID.getText().toString());



            Direccion direccion = new Direccion(numero, calle, comuna, ciudad, idCliente);
            long resultado = dbHelper.InsertarDireccion(direccion);

            if (resultado < 1){
                Toast.makeText(this, "Error, verifique los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
            etNumero.setText("");
            etCalle.setText("");
            etComuna.setText("");
            etCiudad.setText("");
            etClienteID.setText("");
            currentDireccion = null;
            Toast.makeText(this, "Direccion registrado con éxito", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    void fillControls() {
        etNumero.setText(currentDireccion.getNumero());
        etCalle.setText(currentDireccion.getCalle() + "");
        etComuna.setText(currentDireccion.getComuna() + "");
        etCiudad.setText(currentDireccion.getCiudad() + "");
        etClienteID.setText(currentDireccion.getIdCliente() + "");
    }

    private Boolean ActualizarDireccion(){
        if (!etCalle.getText().toString().isEmpty() && !etClienteID.getText().toString().isEmpty()){
            String numero = etNumero.getText().toString();
            String calle = etCalle.getText().toString();
            String comuna = etComuna.getText().toString();
            String ciudad = etCiudad.getText().toString();
            Integer idCliente = Integer.parseInt(etClienteID.getText().toString());


            currentDireccion.setNumero(numero);
            currentDireccion.setCalle(calle);
            currentDireccion.setComuna(comuna);
            currentDireccion.setCiudad(ciudad);
            currentDireccion.setIdCliente(idCliente);
            long resultado = dbHelper.ActualizarDireccion(currentDireccion);

            if (resultado < 1){
                Toast.makeText(this, "Error, verifique los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
            etNumero.setText("");
            etCalle.setText("");
            etComuna.setText("");
            etCiudad.setText("");
            etClienteID.setText("");
            currentDireccion = null;
            Toast.makeText(this, "Direccion actualizado con éxito", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    
}