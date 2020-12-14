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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ne.R;
import com.example.ne.clases.listElementPermiso;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dialogAddTransaccion extends AppCompatDialogFragment {

    private dialogReloadDataTransaccionesListener listener;


    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";


    List<String> permisosC;
    List<String> permisosS;
    List<String> trabajadoresC;
    List<String> trabajadoresS;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_addtransaccion,null);

        Spinner dropdownPermisos = view.findViewById(R.id.spinnerPermiso);
        Spinner dropdownTrabajadores = view.findViewById(R.id.spinnerTrabajador);
        Spinner dropdownHoras = view.findViewById(R.id.spinnerHoras);

        permisosC = new ArrayList<String>();
        permisosS = new ArrayList<String>();

        trabajadoresC = new ArrayList<String>();
        trabajadoresS = new ArrayList<String>();

        chargePermisos();
        chargeTrabajadores();

        String[] items1 = new String[]{"ppppppppppppp1", "ppppppppppppp12", "ppppppppppppp1three"};
        String[] items2 = new String[]{"asdsadasd", "f sdfsdfsdf", "2343assdaasdasd"};
        String[] items3 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,permisosS);
        dropdownPermisos.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,trabajadoresS);
        dropdownTrabajadores.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,items3);
        dropdownHoras.setAdapter(adapter3);



//        editTextNombre = view.findViewById(R.id.editTextAddTrabajadorNombreApellido);

        builder.setView(view)
                .setTitle("Agregar Transaccion")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        dropdownPermisos.getSelectedItem();
 //                       dropdownTrabajadores.getSelectedItem();
                        String p1 = ""+getPost(permisosS,dropdownPermisos.getSelectedItem().toString());
                        String p2 = ""+getPost(trabajadoresS,dropdownTrabajadores.getSelectedItem().toString());
                        String p3 = dropdownHoras.getSelectedItem().toString();
          /*              Toast.makeText(getActivity(),
                                "" + p1 +
                                " " +  p2 + " " + p3,
                                Toast.LENGTH_SHORT).show();
            */
                        String query = "INSERT INTO `transaccionespermisos`(`TraPerTipPerCod`, `TraPerTraCod`, `TraPerHor`, `TraPerEst`) VALUES ("+p1+","+p2+","+p3+",'A')";
                        agregarTransaccion(query);
                        Toast.makeText(getActivity(), "Se agregó la transacción", Toast.LENGTH_SHORT).show();
                        listener.applyReloadAddTransacciones();
                        /*
                        String name = editTextNombre.getText().toString();
                        String query = "INSERT INTO `trabajadores`(`TraNom`, `TraEst`) VALUES ('" + name + "','A')";
                        agregarTrabajador(query);
                        Toast.makeText(getActivity(), "Se agregó el trabajador", Toast.LENGTH_SHORT).show();
                        listener.applyReloadAddTrabajadores();
                        */

                    }

                });
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (dialogReloadDataTransaccionesListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }


    public interface dialogReloadDataTransaccionesListener{
        void applyReloadAddTransacciones();
    }

    public void agregarTransaccion(String query){
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
