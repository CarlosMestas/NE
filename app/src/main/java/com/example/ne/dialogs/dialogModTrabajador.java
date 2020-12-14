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

public class dialogModTrabajador extends AppCompatDialogFragment {

    private EditText editTextNombre;
    private RadioButton radioButtonA;
    private RadioButton radioButtonI;
    private RadioButton radioButtonE;
    private dialogModTrabajador.dialogReloadDataTrabajadoresListener listener;


    private String codigo;
    private String nombre;
    private String estado;

    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    public dialogModTrabajador(String codigo, String nombre, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_modtrabajador,null);

        editTextNombre = view.findViewById(R.id.editTextModTrabajadorNombreApellido);
        radioButtonA = view.findViewById(R.id.radioButtonModA);
        radioButtonI = view.findViewById(R.id.radioButtonModI);
        radioButtonE = view.findViewById(R.id.radioButtonModE);

        editTextNombre.setText(nombre);
        if(estado.equals("A"))
            radioButtonA.setChecked(true);
        else if(estado.equals("I"))
            radioButtonI.setChecked(true);
        else
            radioButtonE.setChecked(true);

        builder.setView(view)
                .setTitle("Modificar Trabajador")
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
                        String query = "UPDATE `trabajadores` SET `TraNom`='" + nameM + "',`TraEst`='" + estadoM +"' WHERE TraCod = " + codigo;
                        modificarTrabajador(query);
                        Toast.makeText(getActivity(), "Se modificó el trabajador", Toast.LENGTH_SHORT).show();
                        listener.applyReloadModTrabajadores();

                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (dialogModTrabajador.dialogReloadDataTrabajadoresListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface dialogReloadDataTrabajadoresListener{
        void applyReloadModTrabajadores();
    }

    public void modificarTrabajador(String query){
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
