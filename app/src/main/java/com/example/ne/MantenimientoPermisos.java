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

public class MantenimientoPermisos extends AppCompatActivity {

    TextView textViewInfo1;
    TextView textViewInfo2;
    TextView textViewInfo3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_permisos);
        textViewInfo1 = (TextView)findViewById(R.id.textViewInfoPermisos1);
        textViewInfo2 = (TextView)findViewById(R.id.textViewInfoPermisos2);
        textViewInfo3 = (TextView)findViewById(R.id.textViewInfoPermisos3);

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
            String result2 = "Nombre \n";
            String result3 = "Estado \n";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tipopermiso");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                result1 += rs.getInt(1) + "\n";
                result2 += rs.getString(2) + "\n";
                if(rs.getBoolean(3))
                    result3 += "Activo" + "\n";
                else
                    result3 += "Inactivo" + "\n";
            }
            textViewInfo1.setText(result1);
            textViewInfo2.setText(result2);
            textViewInfo3.setText(result3);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("ErrorBD", e.toString());
        }
    }

}