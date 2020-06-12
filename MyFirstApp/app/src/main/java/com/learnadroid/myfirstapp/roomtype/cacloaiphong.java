package com.learnadroid.myfirstapp.roomtype;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.timkiemkhachsan.ketquatimkiem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class cacloaiphong extends AppCompatActivity {

    ListView listViewLoaiphong;
    ArrayList<LoaiPhong> arrayloaiphong;
    LoaiPhongAdapter adapter;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    LoaiPhong typeRoom;
    Button back;
    private String hotelId;
    private String checkindate;
    private String checkoutdate;
    private String keyword;

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
        setContentView(R.layout.activity_cacloaiphong);
        getSupportActionBar().hide();

        listViewLoaiphong = (ListView) findViewById(R.id.listLoaiphong);
        arrayloaiphong = new ArrayList<>();
        back = findViewById(R.id.btQuaylai1);

        connectionClass = new ConnectionClass();

        progressDialog = new ProgressDialog(this);

        adapter = new LoaiPhongAdapter(this, R.layout.mau_loai_phong, arrayloaiphong);
        //lấy thông tin id_hotel và thời gian checkin checkout
        Intent intent1 = getIntent();
        hotelId = intent1.getStringExtra("hotelId");
        checkindate = intent1.getStringExtra("checkindate");
        checkoutdate = intent1.getStringExtra("checkoutdate");
        keyword = intent1.getStringExtra("keyword");

//        Toast.makeText(getApplicationContext(), "hotel ID: " + hotelId, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), "checkindate: " + checkindate, Toast.LENGTH_LONG).show();

        Anhxa ax = new Anhxa();
        ax.execute();

        listViewLoaiphong.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiPhong tr = arrayloaiphong.get(position);
                Intent intent = new Intent(cacloaiphong.this, Xacnhan.class);
                intent.putExtra("roomtypeId",Integer.toString(tr.getId()));
                intent.putExtra("hotelId",hotelId);
                intent.putExtra("checkindate",checkindate);
                intent.putExtra("checkoutdate",checkoutdate);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cacloaiphong.this, ketquatimkiem.class);
                intent.putExtra("keyword",keyword);
                intent.putExtra("checkindate",checkindate);
                intent.putExtra("checkoutdate",checkoutdate);
                startActivity(intent);
            }
        });

    }

    private class Anhxa extends AsyncTask<String, String, String> {

        String z = "Select room type.";

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Please check your internet connection";
                } else {

                    //lay id
                    String query= "SELECT * FROM roomtype where id_roomtype in (select id_roomtype from room where id_hotel="+hotelId+")";

                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            float price = rs.getFloat(3);
                            float sale = rs.getFloat(4);
                            String description = rs.getString(5);
                            String bedType = rs.getString(6);
                            Float acreage = rs.getFloat(7);
                            float pricesale = price - sale*price;
                            String image = rs.getString(8);
                            int id_image = getResources().getIdentifier(image,"drawable",getPackageName());
                            typeRoom = new LoaiPhong(id,name,bedType,"Diện tích : "+acreage+" m2",description,"Giá: "+price+"đ","Khuyến mại "+sale*100+"% : "+pricesale+"đ", id_image);
                            arrayloaiphong.add(typeRoom);
                        }
                    } catch (Exception e) {
                        z = "Exceptions: " + e;
                    }

                }
            } catch (Exception ex) {

                z = "Exceptions" + ex;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getBaseContext(), "" + z, Toast.LENGTH_LONG).show();
            listViewLoaiphong.setAdapter(adapter);
            progressDialog.hide();
        }
    }
}
