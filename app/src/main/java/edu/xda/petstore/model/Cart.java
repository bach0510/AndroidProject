package edu.xda.petstore.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
@Keep
public class Cart {
    @PrimaryKey(autoGenerate = true)
    int id ;

    @NonNull
    int userId;
    @NonNull
    int petId;
    @NonNull
    int qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Cart( int userId, int petId ,int qty) {
        this.userId = userId;
        this.petId = petId;
        this.qty = qty;
    }
}
