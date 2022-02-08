package edu.xda.petstore.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.User;

@Dao
public interface CartDAO {

    @Insert
    void insertCart(Cart cart);

    @Update
    void updateCart(Cart cart);

//    @Delete
//    void deleteCart(int id);

    @Query("select * from cart where userId = :userId")
    List<Cart> searchCart(int userId);

    @Query("select * from cart where userId = :userId and petId = :petId")
    Cart findCart(int userId,int petId);

    @Query("Delete from cart where id = :id")
    void deleteCartById(int id);

}
