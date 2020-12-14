package com.example.ne.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ne.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dialogModTransaccion extends AppCompatDialogFragment {



    private RadioButton radioButtonATra;
    private RadioButton radioButtonITra;
    private RadioButton radioButtonETra;

    private dialogModTransaccion.dialogReloadDataTransaccionesListener listener;

    private String codigo;
    private String permiso;
    private String trabajador;
    private String horas;
    private String estado;

    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    List<String> permisosC;
    List<String> permisosS;
    List<String> trabajadoresC;
    List<String> trabajadoresS;
    List<String> horasC;
    List<String> horasS;

    List<String> permisosSComplete;
    List<String> trabajadoresSComplete;



    private EditText editTextTP1;
    private EditText editTextTP2;
    private EditText editTextTP3;

    public dialogModTransaccion(String codigo, String permiso, String trabajador, String horas, String estado) {
        this.codigo = codigo;
        this.permiso = permiso;
        this.trabajador = trabajador;
        this.horas = horas;
        this.estado = estado;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_modtransaccion, null);

        Spinner dropdownPermisos = view.findViewById(R.id.spinnerPermisoTra);
        Spinner dropdownTrabajadores = view.findViewById(R.id.spinnerTrabajadorTra);
        Spinner dropdownHoras = view.findViewById(R.id.spinnerHorasTra);

        editTextTP1 = view.findViewById(R.id.etPTra);
        editTextTP2 = view.findViewById(R.id.etPTra2);
        editTextTP3 = view.findViewById(R.id.etPTra3);

        permisosC = new ArrayList<String>();
        permisosS = new ArrayList<String>();

        trabajadoresC = new ArrayList<String>();
        trabajadoresS = new ArrayList<String>();

        horasC = new ArrayList<String>();
        horasS = new ArrayList<String>();

        permisosSComplete = new ArrayList<>();
        trabajadoresSComplete = new ArrayList<>();

        chargePermisos();
        chargeTrabajadores();

        chargePermisosComplete();
        chargeTrabajadoresComplete();

        for(int i = 0; i < 20 ; i++)
            horasS.add("" + (i + 1));

        final int[] realPermisoPos = new int[1];
        final String[] newPermiso = new String[1];

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,permisosS);
        dropdownPermisos.setAdapter(adapter1);

        dropdownPermisos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newPermiso[0] = permisosS.get(position);
                realPermisoPos[0] = getPost(permisosSComplete, newPermiso[0]);
  //              Toast.makeText(getActivity(), newPermiso + "->" + realPermisoPos, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] newTrabajador = new String[1];
        final int[] realTrabajadorPos = new int[1];

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,trabajadoresS);
        dropdownTrabajadores.setAdapter(adapter2);

        dropdownTrabajadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTrabajador[0] = trabajadoresS.get(position);
                realTrabajadorPos[0] = getPost(trabajadoresSComplete, newTrabajador[0]);
//                Toast.makeText(getActivity(), newTrabajador, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] newHora = new String[1];

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,horasS);
        dropdownHoras.setAdapter(adapter3);

        dropdownHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newHora[0] = horasS.get(position);
//                Toast.makeText(getActivity(), newHora[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioButtonATra = view.findViewById(R.id.radioButtonModATra);
        radioButtonITra = view.findViewById(R.id.radioButtonModITra);
        radioButtonETra = view.findViewById(R.id.radioButtonModETra);

        editTextTP1.setText("Permiso: " + permiso);
        editTextTP2.setText("Trabajador: " + trabajador);
        editTextTP3.setText("Horas: " + horas);



        if(estado.equals("A"))
            radioButtonATra.setChecked(true);
        else if(estado.equals("I"))
            radioButtonITra.setChecked(true);
        else
            radioButtonETra.setChecked(true);

        builder.setView(view)
                .setTitle("Modificar Transacción")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String estadoM;
                        if(radioButtonATra.isChecked())
                            estadoM = "A";
                        else if(radioButtonITra.isChecked())
                            estadoM = "I";
                        else
                            estadoM = "*";
                        String query = "UPDATE `transaccionespermisos` " +
                                "SET `TraPerTipPerCod`="+ realPermisoPos[0]+"," +
                                "`TraPerTraCod`="+ realTrabajadorPos[0] +
                                ",`TraPerHor`=" + newHora[0] +
                                ",`TraPerEst`='" + estadoM + "' " +
                                "WHERE `TraPerCod` = " + codigo;
                        modificarTransaccion(query);
                        Toast.makeText(getActivity(), "Se modificó la transacción", Toast.LENGTH_SHORT).show();
                        listener.applyReloadModTransacciones();

                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (dialogModTransaccion.dialogReloadDataTransaccionesListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface dialogReloadDataTransaccionesListener{
        void applyReloadModTransacciones();
    }

    public void modificarTransaccion(String query){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();

        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void chargePermisos(){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT `TipPerCod`, `TipPerNom` FROM `tipopermiso` WHERE TipPerEst = 'A'");

            while(rs.next()){
                permisosC.add("" + rs.getInt(1));
                permisosS.add(rs.getString(2));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void chargePermisosComplete(){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT `TipPerCod`, `TipPerNom` FROM `tipopermiso`");

            while(rs.next()){
                permisosSComplete.add(rs.getString(2));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void chargeTrabajadores(){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT `TraCod`, `TraNom` FROM `trabajadores` WHERE `TraEst` = 'A'");

            while(rs.next()){
                trabajadoresC.add("" + rs.getInt(1));
                trabajadoresS.add(rs.getString(2));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

    public void chargeTrabajadoresComplete(){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT `TraCod`, `TraNom` FROM `trabajadores`");

            while(rs.next()){
                trabajadoresSComplete.add(rs.getString(2));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }


    public int getPost(List<String> list, String s){
        int p = 0;
        for(int i = 0 ; i < list.size() ; i++){
            if(list.get(i).equals(s)) {
                p = i + 1;
                break;
            }
        }
        return p;

    }
}
