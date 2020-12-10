package com.example.ne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;


public class Login extends AppCompatActivity {

    private static final String url = "jdbc:mysql://192.168.0.6:3306/ne";
    private static final String user = "cmestas";
    private static final String password = "123456";

    EditText editTextUser;
    EditText editTextPassword;

    String userET;
    String passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonSend = (Button) findViewById(R.id.buttonSend);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userET = editTextUser.getText().toString();
                passwordET = editTextPassword.getText().toString();
                testDB();
            }
        });

    }

    public void testDB(){
        TextView textView = (TextView)findViewById(R.id.textViewTest);
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            String result = "Conexión a la base de datos exitosa \n";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Usuarios");
            ResultSetMetaData rsmd = rs.getMetaData();

            boolean search = true;
            while(rs.next()){
                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
                result += rsmd.getColumnName(3) + ": " + rs.getString(3) + "\n";
                result += rsmd.getColumnName(4) + ": " + rs.getBoolean(4) + "\n";
                if(userET.equals(rs.getString(2)) && passwordET.equals(rs.getString(3))){
                    if(rs.getBoolean(4)){
                        Log.d("BD001","Encontro");
                        Toast.makeText(getApplicationContext(), "Bienvenido " + rs.getString(2), Toast.LENGTH_LONG).show();
                        search = true;
                        Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }
                    else{
                        Log.d("BD001","No encontro");
                        Toast.makeText(getApplicationContext(), "Usuario inactivo", Toast.LENGTH_LONG).show();
                        search = true;
                    }
                    break;
                }
                else{
                    search = false;
                }
            }
            //textView.setText(result);
            if(!search){
                Toast.makeText(getApplicationContext(), "No se existe ese usuario", Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
            textView.setText(e.toString());
        }
    }
}