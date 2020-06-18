package com.learnadroid.myfirstapp.timphong;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.ggMap.GoogleMapAPI;
import com.learnadroid.myfirstapp.roomtype.roomTypeResult;
import com.learnadroid.myfirstapp.timkiemkhachsan.checkin;
import com.learnadroid.myfirstapp.timkiemkhachsan.checkout;

public class timphong extends AppCompatActivity {

    private Button search;
    private EditText checkindate;
    private EditText checkoutdate;

    private TextView txtHotelName;
    private ImageView imgHotel;

    private TextView ERcheckin;
    private TextView ERcheckout;
    private TextView ERadults;
    private TextView ERchildrent;
    private EditText editAdult;
    private EditText editChildrent;
    private ImageView image;
    private TextView name;
    private ImageButton back;

    private Dialog checkInDialog;
    private DatePicker datePicker;

    private String CIdate;
    private String COdate;

    private Boolean isValidAdults = false;
    private Boolean isValidChildrent = false;
    private Boolean isValidDate =  false;

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
        setContentView(R.layout.activity_timphong);
        getSupportActionBar().hide();
        checkInDialog = new Dialog(timphong.this);

        txtHotelName = findViewById(R.id.textView2);
        imgHotel = findViewById(R.id.imageView3);
        checkindate = findViewById(R.id.textViewCI2);
        checkoutdate = findViewById(R.id.textViewCO2);
        search = findViewById(R.id.buttonSearch2);
        editAdult = findViewById(R.id.editAdult2);
        editChildrent = findViewById(R.id.editChild2);
        search = findViewById(R.id.buttonSearch2);
        back = findViewById(R.id.imageView10);

        ERadults = findViewById(R.id.ERadults2);
        ERchildrent = findViewById(R.id.ERchildrent2);
        ERcheckin = findViewById(R.id.ERcheckin2);
        ERcheckout = findViewById(R.id.ERcheckout2);

        image = findViewById(R.id.imageView3);
        name = findViewById(R.id.textView2);

        image.setImageResource(AccountManager.image);
        name.setText( AccountManager.hotelName +" Hotel");

        //lấy id_hotel mà API gg map và lịch trả về

        final int hotelId = AccountManager.hotelId;

        txtHotelName.setText(AccountManager.getInstance().hotelSelected.getName());
        imgHotel.setImageResource(AccountManager.getInstance().hotelSelected.getImage1());
        AccountManager.keyword = AccountManager.getInstance().hotelSelected.getCity();

        //lấy thời gian checkin checkout mà lịch trả về
        CIdate = AccountManager.checkindate;
        checkindate.setText(CIdate);

        COdate = AccountManager.checkoutdate;
        checkoutdate.setText(COdate);
        //xử lý sự kiện
        checkindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupCalendar(checkindate);
            }
        });

        checkoutdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopupCalendar(checkoutdate);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isValidDate = checkDate(checkindate.getText().toString(),checkoutdate.getText().toString());

                if(!isValidDate) {

                    ERcheckin.setText("Checkin date must be smaller than checkout date");

                }else if(!isValidAdults || !isValidChildrent || checkoutdate.getText().toString().trim().equals("") || checkindate.getText().toString().trim().equals("")
                        || editAdult.getText().toString().trim().equals("")){
                    ERcheckin.setText("");
                    Toast.makeText(getBaseContext(), "Please check all field again !", Toast.LENGTH_LONG).show();

                }else {
                    Intent intent = new Intent(timphong.this, roomTypeResult.class);
                    AccountManager.checkoutdate = checkoutdate.getText().toString();
                    AccountManager.checkindate = checkindate.getText().toString();
                    startActivity(intent);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(timphong.this, GoogleMapAPI.class);
                startActivity(intent);
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

    private void ShowPopupCalendar(final EditText date) {
        checkInDialog.setContentView(R.layout.popup_calendar);
        TextView txtclose;
        Button btnConfirm;

        datePicker = checkInDialog.findViewById(R.id.checkInDate);

        txtclose = checkInDialog.findViewById(R.id.txtclosePopup);

        btnConfirm = checkInDialog.findViewById(R.id.btnConfirm);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInDialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.setText(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1));
                checkInDialog.dismiss();
            }
        });



        checkInDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        checkInDialog.show();
    }

    private Boolean checkDate(String checkindate, String checkoutdate){
        if(!checkindate.trim().equals("") && !checkoutdate.trim().equals("")){
            if( Integer.parseInt(checkindate.split("/")[1])  > Integer.parseInt(checkoutdate.split("/")[1])){
                return false;
            }else if(Integer.parseInt(checkindate.split("/")[0])  > Integer.parseInt(checkoutdate.split("/")[0])){
                return false;
            }
        }
        return true;
    }

}
