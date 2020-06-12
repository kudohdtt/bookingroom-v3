package com.learnadroid.myfirstapp.actor;

public class hotelOrdered {

    private int hotelId;

    public hotelOrdered(int hotelId, int roomId, String ten, String thanhpho, String trangthai, int hinh, int cham) {
        this.hotelId = hotelId;
        this.roomId = roomId;
        Ten = ten;
        Thanhpho = thanhpho;
        Trangthai = trangthai;
        Hinh = hinh;
        Cham = cham;
    }

    private int roomId;
    private String Ten;
    private String Thanhpho;
    private String Trangthai;
    private int Hinh;
    private int Cham;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getThanhpho() {
        return Thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        Thanhpho = thanhpho;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public int getCham() {
        return Cham;
    }

    public void setCham(int cham) {
        Cham = cham;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
