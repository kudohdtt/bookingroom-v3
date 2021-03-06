package com.learnadroid.myfirstapp.dangnhap;


import com.learnadroid.myfirstapp.actor.Hotel;
import com.learnadroid.myfirstapp.actor.User;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {

    private static AccountManager instance;
    public static  int hotelId;
    public static int userId;
    public  static  int customerId;
    public  static String  checkindate="";
    public static  String checkoutdate="";
    public static String keyword="";
    public static int roomId;
    public static int roomtypeId;
    public static String gmail="";
    public static int bookingId;
    public static int serviceId;
    public static int newId;
    public  static int image;
    public static String hotelName="";

    public static AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    public User user;

    public void InitAccount(int id, String email, String fullName, String numberPhone, String sex, String birthDay, String userName, String passWord){
        user = new User(id, email, fullName, numberPhone, sex, birthDay, userName, passWord);
    }

    public Hotel hotelSelected;



}
