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
import android.widget.TextView;

import com.example.ne.adapters.listAdapterPermiso;
import com.example.ne.adapters.listAdapterTrabajador;
import com.example.ne.clases.listElementPermiso;
import com.example.ne.clases.listElementTrabajador;
import com.example.ne.dialogs.dialogAddPermiso;
import com.example.ne.dialogs.dialogAddTrabajador;
import com.example.ne.dialogs.dialogModPermiso;
import com.example.ne.dialogs.dialogModTrabajador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoPermisos extends AppCompatActivity implements dialogModPermiso.dialogReloadDataPermisosListener, dialogAddPermiso.dialogReloadDataPermisosListener {

    List<listElementPermiso> permisos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_permisos);

        FloatingActionButton fabAdd = findViewById(R.id.fabAddP);
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
            ResultSet rs = st.executeQuery("select * from tipopermiso");

            while(rs.next()){
                String color;
                if(rs.getString(3).equals("A"))
                    color = "#1DA732";
                else if(rs.getString(3).equals("I"))
                    color = "#005DFF";
                else
                    color = "#F93737";
                permisos.add(
                        new listElementPermiso(
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
            ResultSet rs = st.executeQuery("SELECT `TipPerCod`, `TipPerNom`, `TipPerEst` FROM `tipopermiso` WHERE TipPerNom LIKE '"+ q +"%'");

            while(rs.next()){
                String color;
                if(rs.getString(3).equals("A"))
                    color = "#1DA732";
                else if(rs.getString(3).equals("I"))
                    color = "#005DFF";
                else
                    color = "#F93737";
                permisos.add(
                        new listElementPermiso(
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
        permisos = new ArrayList<>();
        listAdapterPermiso listAdapterPermiso = new listAdapterPermiso(permisos,this);
        RecyclerView recyclerView = findViewById(R.id.listRVPermisos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapterPermiso);
    }

    public void openAddDialog(){
        dialogAddPermiso dialog = new dialogAddPermiso();
        dialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void applyReloadModPermisos() {
        chargeData();
    }

    @Override
    public void applyReloadAddPermisos() {
        chargeData();
    }
}