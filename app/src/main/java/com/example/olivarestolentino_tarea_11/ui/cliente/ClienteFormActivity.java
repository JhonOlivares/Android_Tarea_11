package com.example.olivarestolentino_tarea_11.ui.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olivarestolentino_tarea_11.R;
import com.example.olivarestolentino_tarea_11.db.DBHelper;
import com.example.olivarestolentino_tarea_11.modelo.Cliente;

public class ClienteFormActivity extends AppCompatActivity {

    private EditText etNombre, etSaldo, etLimiteCredito, etDescuento;

    private DBHelper dbHelper;

    Cliente currentCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_form);
        dbHelper = new DBHelper(getBaseContext());


        etNombre = findViewById(R.id.et_nombre);
        etSaldo = findViewById(R.id.et_saldo);
        etLimiteCredito = findViewById(R.id.et_limiteCredito);
        etDescuento = findViewById(R.id.et_descuento);

        Intent intent = getIntent();
        currentCliente = (Cliente) intent.getSerializableExtra("obj");
        if (currentCliente != null){
            fillControls();
        }



        findViewById(R.id.btn_guardarCliente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean iscompleted;
                if (currentCliente != null && currentCliente.getIdCliente() > 0){
                    iscompleted = ActualizarCliente();
                } else{
                    iscompleted = RegistrarCliente();
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


    private Boolean RegistrarCliente(){
        if (!etNombre.getText().toString().isEmpty()){
            String nombre = etNombre.getText().toString();

            double saldo = 0;
            if (!etSaldo.getText().toString().isEmpty()) saldo = Double.parseDouble(etSaldo.getText().toString());

            double limeteCredito = 0;
            if(!etLimiteCredito.getText().toString().isEmpty()) limeteCredito = Double.parseDouble(etLimiteCredito.getText().toString());

            double descuento = 0;
            if(!etDescuento.getText().toString().isEmpty()) descuento = Double.parseDouble(etDescuento.getText().toString());

            Cliente cliente = new Cliente(nombre, saldo, limeteCredito, descuento);
            long resultado = dbHelper.InsertarCliente(cliente);

            if (resultado < 1){
                Toast.makeText(this, "Error, verifique los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
            etNombre.setText("");
            etSaldo.setText("");
            etLimiteCredito.setText("");
            etDescuento.setText("");
            currentCliente = null;
            Toast.makeText(this, "Cliente registrado con éxito", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    void fillControls() {
        etNombre.setText(currentCliente.getNombre());
        etSaldo.setText(currentCliente.getSaldo() + "");
        etLimiteCredito.setText(currentCliente.getLimiteCredito() + "");
        etDescuento.setText(currentCliente.getDescuento() + "");
    }

    private Boolean ActualizarCliente(){
        if (!etNombre.getText().toString().isEmpty()){
            String nombre = etNombre.getText().toString();

            double saldo = 0;
            if (!etSaldo.getText().toString().isEmpty()) saldo = Double.parseDouble(etSaldo.getText().toString());

            double limeteCredito = 0;
            if(!etLimiteCredito.getText().toString().isEmpty()) limeteCredito = Double.parseDouble(etLimiteCredito.getText().toString());

            double descuento = 0;
            if(!etDescuento.getText().toString().isEmpty()) descuento = Double.parseDouble(etDescuento.getText().toString());

            currentCliente.setNombre(nombre);
            currentCliente.setSaldo(saldo);
            currentCliente.setLimiteCredito(limeteCredito);
            currentCliente.setDescuento(descuento);
            long resultado = dbHelper.ActualizarCliente(currentCliente);

            if (resultado < 1){
                Toast.makeText(this, "Error de actualizacion, verifique los campos", Toast.LENGTH_SHORT).show();
                return false;
            }
            etNombre.setText("");
            etSaldo.setText("");
            etLimiteCredito.setText("");
            etDescuento.setText("");
            currentCliente = null;
            Toast.makeText(this, "Cliente actualizado con éxito", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}