package com.learnadroid.myfirstapp.timkiemkhachsan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangki.Main2Activity;
import com.learnadroid.myfirstapp.dangki.Main3Activity;
import com.learnadroid.myfirstapp.dangki.dangki;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.gmail.SendMail;

import java.util.Random;

public class maXacNhan extends AppCompatActivity {

    Button btTieptuc2;
    Button bt1;
    String gmail;
    int keyCf;
    private EditText cf;
    Button huy;
    private TextView note;

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
        setContentView(R.layout.activity_ma_xac_nhan);
        btTieptuc2 = (Button) findViewById(R.id.btTieptuc2);
        bt1 = (Button) findViewById(R.id.bt1);
        gmail = AccountManager.gmail;
        cf = findViewById(R.id.txtKhachsan);
        huy = findViewById(R.id.btHuy);
        note = findViewById(R.id.textView2);
        note.setText("Chúng tôi vừa gửi mã xác nhận bảo mật tới email " +gmail);
        btTieptuc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyCf2 = cf.getText().toString().trim();
                if(keyCf2.equals(Integer.toString(keyCf))){
                    Intent mh3 = new Intent(maXacNhan.this, comfim.class);
                    startActivity(mh3);
                } else{
                    Toast.makeText(getBaseContext(),"Mã xác nhận không đúng mời nhập lại",Toast.LENGTH_LONG).show();
                }

            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                });
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(maXacNhan.this, timkiem.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmail() {
        Random rd = new Random();
        keyCf = rd.nextInt((9999-10000 +1)+10000);
        //Getting content for email
        String subject = "[COMFIM YOUR BOOIKNGROOM ACCOUNT]";
        String message = "Mã xác thực tài khoản của bạn là : " +keyCf;

        //Creating SendMail object
        SendMail sm = new SendMail(this, gmail, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}
