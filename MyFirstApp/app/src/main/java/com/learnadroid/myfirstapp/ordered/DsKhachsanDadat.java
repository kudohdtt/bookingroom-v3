package com.learnadroid.myfirstapp.ordered;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.actor.hotelOrdered;
import com.learnadroid.myfirstapp.actor.RoomType;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;
import com.learnadroid.myfirstapp.database.ConnectionClass;
import com.learnadroid.myfirstapp.roomtype.roomResult;
import com.learnadroid.myfirstapp.roomtype.roomTypeResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DsKhachsanDadat extends Fragment {

    ListView listViewHotel;
    ArrayList<hotelOrdered> arrayListHotel;
    hotelOrderedAdapter adapter;
    ProgressDialog progressDialog;
    ConnectionClass connectionClass;
    int customerId;
    int stateIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.activity_ds_khachsan_dadat, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        connectionClass = new ConnectionClass();
        progressDialog = new ProgressDialog(getContext());

        customerId = AccountManager.customerId;
        listViewHotel = getView().findViewById(R.id.listviewCacphong);
        arrayListHotel = new ArrayList<>();
        stateIcon = R.drawable.cham1;

        Anhxa ax = new Anhxa();
        ax.execute();

        adapter = new hotelOrderedAdapter(getContext(), R.layout.hotel_ordered, arrayListHotel);
        listViewHotel.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hotelOrdered tr = arrayListHotel.get(position);
                Intent intent = new Intent(getContext(), orderDetail.class);
                AccountManager.roomId = tr.getRoomId();
                AccountManager.hotelId = tr.getHotelId();
                progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }

    private class Anhxa extends AsyncTask<String, String, String> {

        String z = "Load complete!";
        Boolean result = false;

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

                    String query = "select booking.id_room, room.id_hotel, hotel.name ,city.name ,booking.state, hotel.image from booking inner JOIN room on booking.id_room = room.id_room inner join hotel on "
                            + "room.id_hotel =  hotel.id_hotel inner join city on hotel.id_city = city.id_city WHERE booking.id_customer=" + customerId;

                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            result = true;
                            int id = rs.getInt(1);
                            int id_hotel = rs.getInt(2);
                            String state = rs.getString(5);
                            String name = rs.getString(3);
                            String city = rs.getString(4);
                            String image = rs.getString(6);
                            int id_image = getActivity().getResources().getIdentifier(image, "drawable", getActivity().getPackageName());
                            if (state.trim().equals("wait for checkin")) {
                                stateIcon = R.drawable.cham;
                            } else if (state.trim().equals("wait for check out")) {
                                stateIcon = R.drawable.cham2;
                            }
                            hotelOrdered hotel = new hotelOrdered(id_hotel,id,name, city, state, id_image, stateIcon);
                            arrayListHotel.add(hotel);
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
            Toast.makeText(getContext(), "" + z, Toast.LENGTH_LONG).show();
            listViewHotel.setAdapter(adapter);
            progressDialog.hide();
        }
    }
}
