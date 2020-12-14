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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ne.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class dialogModPermiso extends AppCompatDialogFragment {

    private EditText editTextNombre;
    private RadioButton radioButtonA;
    private RadioButton radioButtonI;
    private RadioButton radioButtonE;

    private dialogModPermiso.dialogReloadDataPermisosListener listener;

    private String codigo;
    private String nombre;
    private String estado;

    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    public dialogModPermiso(String codigo, String nombre, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_modpermiso,null);

        editTextNombre = view.findViewById(R.id.editTextModPermisoNombre);
        radioButtonA = view.findViewById(R.id.radioButtonModAP);
        radioButtonI = view.findViewById(R.id.radioButtonModIP);
        radioButtonE = view.findViewById(R.id.radioButtonModEP);

        editTextNombre.setText(nombre);
        if(estado.equals("A"))
            radioButtonA.setChecked(true);
        else if(estado.equals("I"))
            radioButtonI.setChecked(true);
        else
            radioButtonE.setChecked(true);

        builder.setView(view)
                .setTitle("Modificar Permiso")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nameM = editTextNombre.getText().toString();
                        String estadoM;
                        if(radioButtonA.isChecked())
                            estadoM = "A";
                        else if(radioButtonI.isChecked())
                            estadoM = "I";
                        else
                            estadoM = "*";
                        String query = "UPDATE `tipopermiso` SET `TipPerNom`='" + nameM + "',`TipPerEst`='" + estadoM +"' WHERE TipPerCod = " + codigo;
                        modificarPermiso(query);
                        Toast.makeText(getActivity(), "Se modificó el permiso", Toast.LENGTH_SHORT).show();
                        listener.applyReloadModPermisos();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (dialogModPermiso.dialogReloadDataPermisosListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface dialogReloadDataPermisosListener{
        void applyReloadModPermisos();
    }

    public void modificarPermiso(String query){
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

}
