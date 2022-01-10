package edu.xda.petstore.model;

public class PetCartDto {
    int id ;
    int cartId ;
    int loaiId ;
    String maGiong;
    String tenGiong;
    String moTa;
    int donGia;
    byte[] anh;

    public PetCartDto(int id, int cartId, int loaiId, String maGiong, String tenGiong, String moTa, int donGia, byte[] anh) {
        this.id = id;
        this.cartId = cartId;
        this.loaiId = loaiId;
        this.maGiong = maGiong;
        this.tenGiong = tenGiong;
        this.moTa = moTa;
        this.donGia = donGia;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getLoaiId() {
        return loaiId;
    }

    public void setLoaiId(int loaiId) {
        this.loaiId = loaiId;
    }

    public String getMaGiong() {
        return maGiong;
    }

    public void setMaGiong(String maGiong) {
        this.maGiong = maGiong;
    }

    public String getTenGiong() {
        return tenGiong;
    }

    public void setTenGiong(String tenGiong) {
        this.tenGiong = tenGiong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
