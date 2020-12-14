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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ne.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class dialogAddPermiso extends AppCompatDialogFragment {

    private EditText editTextNombre;

    private dialogReloadDataPermisosListener listener;


    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_addpermiso,null);

        editTextNombre = view.findViewById(R.id.editTextAddTransaccion);

        builder.setView(view)
                .setTitle("Agregar Permiso")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextNombre.getText().toString();
                        String genero = "M";

                        String query = "INSERT INTO `tipopermiso`(`TipPerNom`, `TipPerEst`) VALUES ('" + name + "','A')";
                        agregarPermiso(query);
                        Toast.makeText(getActivity(), "Se agregó el permiso", Toast.LENGTH_SHORT).show();
                        listener.applyReloadAddPermisos();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (dialogReloadDataPermisosListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface dialogReloadDataPermisosListener{
        void applyReloadAddPermisos();
    }

    public void agregarPermiso(String query){
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
