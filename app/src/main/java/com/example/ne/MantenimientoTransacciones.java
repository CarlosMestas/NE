package com.example.ne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MantenimientoTransacciones extends AppCompatActivity {

    TextView textViewInfo1;
    TextView textViewInfo2;
    TextView textViewInfo3;
    TextView textViewInfo4;
    TextView textViewInfo5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_transacciones);

        textViewInfo1 = (TextView)findViewById(R.id.textViewInfoTransacciones1);
        textViewInfo2 = (TextView)findViewById(R.id.textViewInfoTransacciones2);
        textViewInfo3 = (TextView)findViewById(R.id.textViewInfoTransacciones3);
        textViewInfo4 = (TextView)findViewById(R.id.textViewInfoTransacciones4);
        textViewInfo5 = (TextView)findViewById(R.id.textViewInfoTransacciones5);

        testDB();

    }


    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    public void testDB(){
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String result1 = "Cod \n";
            String result2 = "Permiso \n";
            String result3 = "Trabajador \n";
            String result4 = "Horas \n";
            String result5 = "Estado \n";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT TraPerCod, tipopermiso.TipPerNom, trabajadores.TraNom, TraPerHor, TraPerEst " +
                    "FROM transaccionespermisos INNER JOIN tipopermiso ON transaccionespermisos.TraPerTipPerCod = tipopermiso.TipPerCod " +
                    "INNER JOIN trabajadores ON transaccionespermisos.TraPerTraCod = trabajadores.TraCod");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                result1 += rs.getInt(1) + "\n\n";
                result2 += rs.getString(2) + "\n\n";
                result3 += rs.getString(3) + "\n";
                result4 += rs.getString(4) + "\n\n";
                if(rs.getBoolean(5))
                    result5 += "Activo" + "\n\n";
                else
                    result5 += "Inactivo" + "\n\n";
            }
            textViewInfo1.setText(result1);
            textViewInfo2.setText(result2);
            textViewInfo3.setText(result3);
            textViewInfo4.setText(result4);
            textViewInfo5.setText(result5);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

}