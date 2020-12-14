package com.example.ne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.ne.adapters.listAdapterTrabajador;
import com.example.ne.clases.listElementTrabajador;
import com.example.ne.dialogs.dialogAddTrabajador;
import com.example.ne.dialogs.dialogModTrabajador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoTrabajdores extends AppCompatActivity implements dialogModTrabajador.dialogReloadDataTrabajadoresListener, dialogAddTrabajador.dialogReloadDataTrabajadoresListener{

    List<listElementTrabajador> trabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_trabajdores);

        FloatingActionButton fabAdd = findViewById(R.id.fabAddT);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDialog();
            }
        });

        chargeData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.searchB);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Ingrese nombre");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return false;
            }
        });

        return true;
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
            ResultSet rs = st.executeQuery("select * from trabajadores");

            while(rs.next()){
                String color;
                if(rs.getString(3).equals("A"))
                    color = "#1DA732";
                else if(rs.getString(3).equals("I"))
                    color = "#005DFF";
                else
                    color = "#F93737";
                trabajadores.add(
                        new listElementTrabajador(
                                "" + rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                color));
                }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void searchData(String q){
        try{
            init();
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT `TraCod`, `TraNom`, `TraEst` FROM `trabajadores` WHERE TraNom LIKE '"+ q +"%'");

            while(rs.next()){
                String color;
                if(rs.getString(3).equals("A"))
                    color = "#1DA732";
                else if(rs.getString(3).equals("I"))
                    color = "#005DFF";
                else
                    color = "#F93737";
                trabajadores.add(
                        new listElementTrabajador(
                                "" + rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                color));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }


    public void init(){
        trabajadores = new ArrayList<>();
        listAdapterTrabajador listAdapterTrabajador = new listAdapterTrabajador(trabajadores,this);
        RecyclerView recyclerView = findViewById(R.id.listRVTrabajadores);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterTrabajador);
    }

    public void openAddDialog(){
        dialogAddTrabajador dialog = new dialogAddTrabajador();
        dialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void applyReloadAddTrabajadores() {
        chargeData();
    }

    @Override
    public void applyReloadModTrabajadores() {
        chargeData();
    }
}