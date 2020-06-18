package com.learnadroid.myfirstapp.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.ggMap.GoogleMapAPI;
import com.learnadroid.myfirstapp.timkiemkhachsan.hotelResult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchHotel extends Fragment {

    private EditText checkindate;
    private EditText checkoutdate;
    private ImageButton ggmap;
    private EditText hotel;
    private Button search;

    private Dialog checkInDialog;
    private DatePicker datePicker;

    //loi
    private TextView ERcity;
    private TextView ERcheckin;
    private TextView ERcheckout;
    private TextView ERadults;
    private TextView ERchildrent;
    private EditText editAdult;
    private EditText editChildrent;


    //bien validate
    private Boolean isValidAdults = false;
    private Boolean isValidChildrent = true;
    private Boolean isValidDate =  false;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.activity_timkiem, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        checkindate = getView().findViewById(R.id.txtCIdate);
        checkoutdate = getView().findViewById(R.id.txtCOdate);
        hotel = getView().findViewById(R.id.edtCity);
        editAdult = getView().findViewById(R.id.editAdult);
        editChildrent = getView().findViewById(R.id.editChild);
        search = getView().findViewById(R.id.buttonSearch);

        checkInDialog = new Dialog(getActivity());



        ERcity = getView().findViewById(R.id.ERcity);
        ERadults = getView().findViewById(R.id.ERadults);
        ERchildrent = getView().findViewById(R.id.ERchildrent);
        ERcheckin = getView().findViewById(R.id.ERcheckin);
        ERcheckout = getView().findViewById(R.id.ERcheckout);



        ggmap = getView().findViewById(R.id.ggmap);

        //gan su kien
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

        ggmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GoogleMapAPI.class);
                startActivity(intent);
            }
        });

       //set cac truong tim kiem

        String hotelname = AccountManager.keyword;
        hotel.setText(hotelname);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidDate = checkDate(checkindate.getText().toString(),checkoutdate.getText().toString());

                if(!isValidDate){

                    ERcheckin.setText("Checkin date must be smaller than checkout date");
                    ERcheckin.setTextColor(Color.rgb(255, 0, 0));
                }else if(!isValidAdults || !isValidChildrent || hotel.getText().toString().trim().equals("") || checkoutdate.getText().toString().trim().equals("")
                        || checkindate.getText().toString().trim().equals("") || editAdult.getText().toString().trim().equals("")){
                    Toast.makeText(getContext(), "Please check all field again !", Toast.LENGTH_LONG).show();
                    ERcheckin.setText("");
                }else {
                    Intent intent = new Intent(getContext(), hotelResult.class);
                    AccountManager.keyword = hotel.getText().toString();
                    AccountManager.checkindate = checkindate.getText().toString();
                    AccountManager.checkoutdate = checkoutdate.getText().toString();
                    startActivity(intent);
                }

            }
        });

        //Số người trưởng thành
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
        //số trẻ em
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
