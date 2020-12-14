package com.example.ne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ne.adapters.listAdapterTrabajador;
import com.example.ne.adapters.listAdapterTransaccion;
import com.example.ne.clases.listElementPermiso;
import com.example.ne.clases.listElementTrabajador;
import com.example.ne.clases.listElementTransaccion;
import com.example.ne.dialogs.dialogAddTrabajador;
import com.example.ne.dialogs.dialogAddTransaccion;
import com.example.ne.dialogs.dialogModTrabajador;
import com.example.ne.dialogs.dialogModTransaccion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoTransacciones extends AppCompatActivity implements dialogAddTransaccion.dialogReloadDataTransaccionesListener, dialogModTransaccion.dialogReloadDataTransaccionesListener {

    List<listElementTransaccion> transacciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_transacciones);

        FloatingActionButton fabAdd = findViewById(R.id.fabAddTra);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });

        chargeData();

    }


    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    public void chargeData(){
        try{
            init();
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT TraPerCod, tipopermiso.TipPerNom, trabajadores.TraNom, TraPerHor, TraPerEst " +
                    "FROM transaccionespermisos INNER JOIN tipopermiso ON transaccionespermisos.TraPerTipPerCod = tipopermiso.TipPerCod " +
                    "INNER JOIN trabajadores ON transaccionespermisos.TraPerTraCod = trabajadores.TraCod");

            while(rs.next()){
                String color;
                if(rs.getString(5).equals("A"))
                    color = "#1DA732";
                else if(rs.getString(5).equals("I"))
                    color = "#005DFF";
                else
                    color = "#F93737";
                transacciones.add(
                        new listElementTransaccion(
                                "" + rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                "" + rs.getInt(4),
                                rs.getString(5),
                                color));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void init(){
        transacciones = new ArrayList<>();
        listAdapterTransaccion listAdapterTransaccion = new listAdapterTransaccion(transacciones,this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTra);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterTransaccion);
    }

    public void openAddDialog(){
        dialogAddTransaccion dialog = new dialogAddTransaccion();
        dialog.show(getSupportFragmentManager(),"");
    }


    @Override
    public void applyReloadAddTransacciones() {
        chargeData();
    }

    @Override
    public void applyReloadModTransacciones() {
        chargeData();
    }
}