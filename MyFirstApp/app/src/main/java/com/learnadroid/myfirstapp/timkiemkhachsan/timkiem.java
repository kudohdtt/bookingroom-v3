package com.learnadroid.myfirstapp.timkiemkhachsan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.ggMap.GoogleMapAPI;
import com.learnadroid.myfirstapp.R;

public class timkiem extends AppCompatActivity {

    private EditText checkindate;
    private EditText checkoutdate;
    private ImageButton ggmap;
    private EditText hotel;
    private Button search;

    //loi
    private TextView ERcity;
    private TextView ERcheckin;
    private TextView ERcheckout;
    private TextView ERadults;
    private TextView ERchildrent;
    private EditText editAdult;
    private EditText editChildrent;
    //private ImageButton profile;

    private String txt1;
    private String txt2;
    private String txt3;
    private String txt4;

    //bien validate
    private Boolean isValidCity = false;
    private Boolean isValidCIdate = false;
    private Boolean isValidCodate = false;
    private Boolean isValidAdults = false;
    private Boolean isValidChildrent = false;

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
        setContentView(R.layout.activity_timkiem);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        checkindate = findViewById(R.id.txtCIdate);
        checkoutdate = findViewById(R.id.txtCOdate);
        hotel = findViewById(R.id.edtCity);
        editAdult = findViewById(R.id.editAdult);
        editChildrent = findViewById(R.id.editChild);
        search = findViewById(R.id.buttonSearch);
        //profile = findViewById(R.id.imageButtonAccount);

        ERcity = findViewById(R.id.ERcity);
        ERadults = findViewById(R.id.ERadults);
        ERchildrent = findViewById(R.id.ERchildrent);
        ERcheckin = findViewById(R.id.ERcheckin);
        ERcheckout = findViewById(R.id.ERcheckout);

        txt1 = hotel.getText().toString();
        txt2 = checkindate.getText().toString();
        txt3 = checkoutdate.getText().toString();
        txt4 = editAdult.getText().toString();

        ggmap = findViewById(R.id.ggmap);

//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(timkiem.this, Profile.class);
//                startActivity(intent);
//            }
//        });

        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkin.class);
                intent.putExtra("checkoutdate",checkoutdate.getText().toString());
                intent.putExtra("hotel",hotel.getText().toString());
                startActivity(intent);
            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, checkout.class);
                intent.putExtra("checkindate",checkindate.getText().toString());
                intent.putExtra("hotel",hotel.getText().toString());
                startActivity(intent);
            }
        });

        ggmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timkiem.this, GoogleMapAPI.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String CIdate = intent.getStringExtra("checkindate");
        checkindate.setText(CIdate);

        String COdate = intent.getStringExtra("checkoutdate");
        checkoutdate.setText(COdate);

        String hotelname = intent.getStringExtra("hotel");
        hotel.setText(hotelname);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isValidCity || !isValidCIdate || !isValidCodate || !isValidAdults || !isValidChildrent || txt1.trim().equals("") || txt2.trim().equals("") || txt3.trim().equals("") || txt4.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Please check all field again !", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(timkiem.this, hotelResult.class);
                    intent.putExtra("keyword",hotel.getText().toString());
                    intent.putExtra("checkindate",checkindate.getText().toString());
                    intent.putExtra("checkoutdate",checkoutdate.getText().toString());
                    startActivity(intent);
                }

            }
        });


        editAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ERadults.setText("");
                String txt = editAdult.getText().toString();
                if(!txt.trim().equals("")) {
                    isValidAdults = (!(Integer.parseInt(txt) > 4));
                }
                if (!isValidAdults) {
                    ERadults.setTextColor(Color.rgb(255, 0, 0));
                    ERadults.setText("Nhỏ hơn 5");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editChildrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ERchildrent.setText("");
                String txt = editChildrent.getText().toString();
                if(!txt.trim().equals("")) {
                    isValidChildrent = (!(Integer.parseInt(txt) > 6));
                }
                if (!isValidChildrent) {
                    ERchildrent.setTextColor(Color.rgb(255, 0, 0));
                    ERchildrent.setText("Nhỏ hơn 7");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
