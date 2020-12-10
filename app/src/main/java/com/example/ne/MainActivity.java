package com.example.ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonPermisos;
    Button buttonTrabajadores;
    Button buttonTransacciones;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPermisos = (Button) findViewById(R.id.buttonPermisos);
        buttonTrabajadores = (Button) findViewById(R.id.buttonTrabjadores);
        buttonTransacciones = (Button) findViewById(R.id.buttonTransacciones);

        buttonPermisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMantenimientoPermsisos();
                startActivity(i);
            }
        });

        buttonTrabajadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMantenimientoTrabajadores();
                startActivity(i);
            }
        });

        buttonTransacciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMantenimientoTransacciones();
                startActivity(i);
            }
        });
    }

    public void startMantenimientoPermsisos(){
        i = new Intent (this.getApplicationContext(), MantenimientoPermisos.class);
        startActivity(i);
    }

    public void startMantenimientoTrabajadores(){
        i = new Intent (this.getApplicationContext(), MantenimientoTrabajdores.class);

    }

    public void startMantenimientoTransacciones(){
        i = new Intent(this.getApplicationContext(), MantenimientoTransacciones.class);
    }


}