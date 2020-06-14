package com.learnadroid.myfirstapp.timkiemkhachsan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.timkiemkhachsan.timkiem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class comfim extends AppCompatActivity {

    private Button start;
    ConnectionClass connectionClass;
    int newId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comfim);
        start = findViewById(R.id.btTieptuc);
        connectionClass = new ConnectionClass();
        AddOrder addOrder = new AddOrder();
        addOrder.execute();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(comfim.this, timkiem.class);
                startActivity(intent);
            }
        });
    }
    public class AddOrder extends AsyncTask<String, String, String> {

        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            int id = 0;
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {
                    String queryId = "SELECT * FROM booking ORDER BY id_booking DESC LIMIT 1";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(queryId);
                    while (rs.next()) {
                        id = Integer.parseInt(rs.getString(1));
                        id += 1;
                    }

                    newId = id;

                    String query = "insert into booking values('" +newId +"','"+ AccountManager.checkindate+"','"+AccountManager.checkoutdate+"','"+AccountManager.roomId+
                            "','"+AccountManager.customerId+"','"+"wait for checkin')";

                    try {
                        Statement stmt1 = con.createStatement();
                        stmt1.executeUpdate(query);
                    } catch (Exception e) {
                        z = z  + e;
                    }

                    z = "The hotel was successfully booked";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exceptions" + ex;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
        }
    }
}