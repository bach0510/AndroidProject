package edu.xda.petstore.model;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet")
@Keep
public class Pet {

    @PrimaryKey(autoGenerate = true)
    int id ;
    int loaiId ;
    String maGiong;
    String tenGiong;
    String moTa;
    int donGia;
    byte[] anh;

    public Pet(int loaiId, String maGiong, String tenGiong, String moTa, int donGia, byte[] anh) {
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
